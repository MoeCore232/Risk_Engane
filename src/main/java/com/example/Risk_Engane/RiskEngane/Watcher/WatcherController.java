package com.example.Risk_Engane.RiskEngane.Watcher;

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
@RequestMapping("/api/watcher")
public class WatcherController {

    @Autowired
    private WatcherService watcherService;

    @GetMapping("/get-all-watchers")
    public ResponseEntity<GlobalResponse<List<Watcher>>> getAllWatchers () {
        List<Watcher> watchers = watcherService.getAllWatchers();
        return new ResponseEntity<>(new GlobalResponse<>(watchers), HttpStatus.OK);
    }

    @DeleteMapping("/delete-watcher/{watcherId}")
    public ResponseEntity<GlobalResponse<String>> deleteWatcher (@PathVariable UUID watcherId) {
        String res = watcherService.deleteWatcher(watcherId);
        return new ResponseEntity<>(new GlobalResponse<>(res), HttpStatus.OK);
    }

}
