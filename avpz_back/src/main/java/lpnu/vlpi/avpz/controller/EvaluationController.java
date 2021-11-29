package lpnu.vlpi.avpz.controller;


import lpnu.vlpi.avpz.converter.evaluation.EvaluationDTO;
import lpnu.vlpi.avpz.dto.result.ResultDTO;
import lpnu.vlpi.avpz.model.UserModel;
import lpnu.vlpi.avpz.service.EvaluationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:3000", maxAge = 360000, allowCredentials = "true")
@RequestMapping("/vlpi/v1/evaluation/{taskUid}")
public class EvaluationController {

    private EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @PostMapping
    public ResponseEntity evaluate(@PathVariable("taskUid") String taskUid, @RequestBody ResultDTO resultDTO, HttpSession httpSession) {
        EvaluationDTO evaluationDTO = new EvaluationDTO();
        resultDTO.setTaskUid(taskUid);
        UserModel currentUser = (UserModel) httpSession.getAttribute("currentUser");
        resultDTO.setUserUid(currentUser.getUid());
        float mark = 0;
        try {
            evaluationDTO = evaluationService.evaluate(resultDTO);
        } catch (Exception exception) {
            return new ResponseEntity<>("Стався піздєц", HttpStatus.I_AM_A_TEAPOT);
        }
        return new ResponseEntity(evaluationDTO, HttpStatus.OK);
    }
}

