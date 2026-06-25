package com.example.Risk_Engane.RiskEngane.Watcher;

import com.example.Risk_Engane.ErrorHandling.CustomResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WatcherService {

    @Autowired
    private WatcherRepo watcherRepo;

    public List<Watcher> getAllWatchers () {
        return watcherRepo.findAll();
    }

    public String deleteWatcher (UUID watcherId) {
        Watcher findWatcher = watcherRepo.findById(watcherId)
                .orElseThrow(() -> CustomResponseException.idIsNotFound(watcherId));
        watcherRepo.deleteById(findWatcher.getId());
        return "Watcher deleted successfully!";
    }

}
