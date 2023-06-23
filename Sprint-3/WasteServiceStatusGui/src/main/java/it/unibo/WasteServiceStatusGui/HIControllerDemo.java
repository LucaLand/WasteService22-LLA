package it.unibo.WasteServiceStatusGui;


import it.unibo.coapobs.CoapObserverJava;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.json.simple.JSONObject;


@Controller
public class HIControllerDemo {

    CoapObserverJava obsGuiUpdater = new CoapObserverJava("localhost:8072", "ctxwasteservice", "guiupdater");


    @Value("${spring.application.name}")
    String appName;

    String robotState = "", robotPos = "", ledState = "", currentPB = "", currentGB = "";

    @GetMapping("/")
    public String homePage(Model model) {
        String currentValue = obsGuiUpdater.getCurrentState();
        if(currentValue.contains("jsonupdate")){
            String stringUpdate = currentValue.split("jsonupdate")[1];
            stringUpdate = stringUpdate.substring(1, stringUpdate.length()-1);
            System.out.println(stringUpdate);
            JSONObject jsonUpdate;
            JSONParser parser = new JSONParser();
            try {
                jsonUpdate = (JSONObject) parser.parse(stringUpdate);
                System.out.println(jsonUpdate);
                robotState = (String) jsonUpdate.get("robotState");
                robotPos = (String) jsonUpdate.get("robotPos");
                ledState = (String) jsonUpdate.get("ledState");
                currentPB = (String) jsonUpdate.get("currentPB");
                currentGB = (String) jsonUpdate.get("currentGB");

            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }



        model.addAttribute("robotState", robotState);
        model.addAttribute("robotPosition", robotPos);
        model.addAttribute("ledState", ledState);
        model.addAttribute("pb", currentPB);
        model.addAttribute("gb", currentGB);
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