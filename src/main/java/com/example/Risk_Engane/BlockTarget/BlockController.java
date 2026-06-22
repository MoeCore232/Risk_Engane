package com.example.Risk_Engane.BlockTarget;

import com.example.Risk_Engane.ErrorHandling.GlobalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/api/block")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @GetMapping("/get-all-blocks")
    public ResponseEntity<GlobalResponse<List<Block>>> getAllBlockedTargets () {
        List<Block> blockedTargets = blockService.getAllBlockedTargets();
        return new ResponseEntity<>(new GlobalResponse<>(blockedTargets), HttpStatus.OK);
    }

    @DeleteMapping("/delete-block/{blockId}")
    public ResponseEntity<GlobalResponse<String>> deleteBlockTarget (@PathVariable UUID blockId) {
        blockService.deleteBlock(blockId);
        return new ResponseEntity<>(new GlobalResponse<>(
                "Block deleted successfully!"
        ), HttpStatus.OK);
    }

}
