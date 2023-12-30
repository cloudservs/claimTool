package com.cloudservs.claimtool.restController;

import com.cloudservs.claimtool.domain.ClientConfig;
import com.cloudservs.claimtool.handler.ClientHandler;
import com.cloudservs.claimtool.utils.SearchParams;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientRestService {
    @Autowired
    ClientHandler clientHandler;

    @PostMapping("/save")
    public String save(@RequestBody ClientConfig config, HttpServletRequest request) {
        return clientHandler.save(config);
    }
    @PostMapping("/fetch")
    public List<Document> retrieve(@RequestBody SearchParams searchParams, HttpServletRequest request) {
        return clientHandler.fetch(request,searchParams);
    }

}
