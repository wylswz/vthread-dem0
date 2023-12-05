package org.xmbsmdsj.vthreaddemo.apis;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/standard")
public class StandardAPI {

    @GetMapping(path = "/sleep")
    public void sleep(@RequestParam Long durationMillis) throws InterruptedException {
        Thread.sleep(durationMillis);
    }




}
