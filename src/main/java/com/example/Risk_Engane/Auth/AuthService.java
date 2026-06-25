package com.example.Risk_Engane.Auth;

import com.example.Risk_Engane.BlockTarget.BlockService;
import com.example.Risk_Engane.Device.Device;
import com.example.Risk_Engane.Device.DeviceService;
import com.example.Risk_Engane.ErrorHandling.CustomResponseException;
import com.example.Risk_Engane.RiskEngane.MainRiskEngine;
import com.example.Risk_Engane.RiskEngane.RiskSession;
import com.example.Risk_Engane.RiskEngane.Watcher.Watcher;
import com.example.Risk_Engane.RiskEngane.Watcher.WatcherRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private AuthRepo authRepo;
    @Autowired
    private BlockService blockService;
    @Autowired
    private MainRiskEngine mainRiskEngine;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private WatcherRepo watcherRepo;

    public List<Auth> getAllAuths () {
        return authRepo.findAll();
    }

    public Auth signUp (AuthDto.SignUp signUp) {
        Auth auth = Auth.createAuth(signUp);
        authRepo.save(auth);
        return auth;
    }

    public Auth findAuthByEmail (String email) {
        Auth findAuth = authRepo.findByEmail(email)
                .orElseThrow(() -> CustomResponseException.publicError("Bad Credentials", 400));
        return findAuth;
    }

    public UUID login (AuthDto.Login login) {

        blockService.isBlocked(login.deviceName());
        blockService.isBlocked(login.email());

        int score = mainRiskEngine.beforeKnowing(login);

        Auth auth = findAuthByEmail(login.email());

        Device device = deviceService.findDeviceByAuthId(auth.getId());

        RiskSession.RiskListsType listsType = mainRiskEngine.afterKnowing(login, device, score);

        Optional<Watcher> watcher = Watcher.createRiskWatcher(listsType, auth.getId());

        watcherRepo.save(watcher.get());

        // محاكاة التوكين في عملية المصادقة
        return UUID.randomUUID();
    }

}
