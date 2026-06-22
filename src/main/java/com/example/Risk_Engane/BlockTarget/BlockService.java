package com.example.Risk_Engane.BlockTarget;

import com.example.Risk_Engane.ErrorHandling.CustomResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class BlockService {


    private static Logger logger = LoggerFactory.getLogger(Block.class);

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Autowired
    private BlockRepo blockRepo;

    @Transactional
    public void isBlocked (String targetValue) {
        Optional<Block> findBlockedTarget = blockRepo.findByTargetValue(targetValue);
        if (findBlockedTarget.isPresent()) {
            Block blockedTarget = findBlockedTarget.get();
            if (blockedTarget.getIsPermanent()) {
                throw CustomResponseException.blockDevice();
            }
            int convertMinutesToHours = 0;
            if (blockedTarget.getHowMinutes() >= 60) {
                convertMinutesToHours += blockedTarget.getHowMinutes() / 60;
                throw CustomResponseException.temporaryBlock(convertMinutesToHours, "hours");
            }
            throw CustomResponseException.temporaryBlock(blockedTarget.getHowMinutes(), "minutes");
        }
    }

    public void blockTarget (String target, String reason) {
        Block block = Block.blockDevice(null, target, reason, null, true, 0);
        blockRepo.save(block);
    }

    public void temporaryBlock (String target, String reason, int minutes) {
        LocalDateTime blockTime = LocalDateTime.now().plusMinutes(minutes);
        Block block = Block.blockDevice(null, target, reason, blockTime, false, minutes);
        blockRepo.save(block);
        scheduler.schedule(() -> {
            blockRepo.deleteById(block.getId());
            logger.info("Blocked {} minutes removed success!", minutes);
        }, minutes, TimeUnit.MINUTES);
    }

    public List<Block> getAllBlockedTargets () {
        try {
            List<Block> blockedTargets = blockRepo.findAll();
            return blockedTargets;
        } catch (Exception e) {
            logger.info("Error: {}", e.getMessage(), e);
            throw CustomResponseException.unExpectedErrorOccurred();
        }
    }

    public void deleteBlock (UUID blockId) {
        Block findBlockedTarget = blockRepo.findById(blockId)
                .orElseThrow(() -> CustomResponseException.idIsNotFound(blockId));
        try {
            blockRepo.deleteById(findBlockedTarget.getId());
        } catch (Exception e) {
            logger.info("Error: {}", e.getMessage(), e);
        }
    }

}
