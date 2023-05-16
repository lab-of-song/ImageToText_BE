package com.songsong.ImageToText.Controller;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

@RestController
public class MassageController {
//    MessageService messageService;
//
//    @Autowired
//    public MassageController(MessageService messageService) {
//        this.messageService = messageService;
//    }
//
//    @PostMapping("/massage")
//    public ResponseEntity<String> fileIo( @RequestParam("img") List<MultipartFile> img){
//
//        String res =messageService.imgToText(img);
//
//
//
//        return new ResponseEntity<String>(res, HttpStatus.OK);
//    }
    @CrossOrigin
    @PostMapping("/tesseract")
    public ResponseEntity<String> extractTextFromImage(@RequestBody String imageRequest) {

        String str2 = imageRequest.split(":")[1];
        imageRequest = str2.substring(1, str2.length()-2);
        System.out.println(imageRequest);

        try {

            // Base64 디코딩하여 이미지 데이터 복구
            byte[] imageData = java.util.Base64.getDecoder().decode(imageRequest.getBytes("UTF-8"));
            BufferedImage bufferedImage = null;
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);
                bufferedImage = ImageIO.read(bis);
            } catch (Exception e) {
                e.printStackTrace();
                // 이미지 변환 오류 처리
            }
            // Tesseract OCR을 사용하여 이미지에서 텍스트 추출
            ITesseract tesseract = new Tesseract();
            tesseract.setLanguage("eng+kor");
            tesseract.setDatapath("/usr/share/tesseract-ocr/4.00/tessdata");
            String extractedText = tesseract.doOCR(bufferedImage);

            StringBuffer sb = new StringBuffer(extractedText);
            System.out.println("================================== 이미지에서 텍스트 추출한 결과 =========================================");
            System.out.println(sb);

            // 추출된 텍스트를 JSON 형식으로 응답
            return new ResponseEntity<>(extractedText, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error occurred while processing the image", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}
