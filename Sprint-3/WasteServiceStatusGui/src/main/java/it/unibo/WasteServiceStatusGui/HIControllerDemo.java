package it.unibo.WasteServiceStatusGui;


import it.unibo.coapobs.CoapObserverJava;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HIControllerDemo {

    CoapObserverJava obsGuiUpdater = new CoapObserverJava("localhost:8072", "ctxwasteservice", "guiupdater");


    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model) {
        /*String values = obsGuiUpdater.getCurrentState();
        values = values.split("(")[1].substring(0, values.length());
        List<String> valuesList = new LinkedList<>();
        while(!Objects.equals(values.split(",")[0], "")){
            valuesList.add(values.split(",")[0]);
            values = values.split(",")[0];
        }*/
        /*String stringUpdate = obsGuiUpdater.getCurrentState();
        JSONObject jsonUpdate;
        if(stringUpdate.contains("jsonupdate")){
            jsonUpdate = JSONObject().
        }*/



        model.addAttribute("robotState", obsGuiUpdater.getCurrentState());
        model.addAttribute("robotPosition",obsGuiUpdater.getCurrentState());
        model.addAttribute("ledState",obsGuiUpdater.getCurrentState());
        model.addAttribute("pb",obsGuiUpdater.getCurrentState());
        model.addAttribute("gb",obsGuiUpdater.getCurrentState());
        return "welcome";
    }

    @ExceptionHandler
    public ResponseEntity handle(Exception ex) {
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity(
                "HIControllerDemo ERROR " + ex.getMessage(),
                responseHeaders, HttpStatus.CREATED);
    }
}