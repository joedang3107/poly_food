package com.example.thuctap.controller;

import com.example.thuctap.models.Cart;
import com.example.thuctap.models.Order;
import com.example.thuctap.models.Payment;
import com.example.thuctap.models.responseobject.ProductResponses;
import com.example.thuctap.onlinepay.Config;
import com.example.thuctap.payload.response.CartResponse;
import com.example.thuctap.payload.response.MessageResponse;
import com.example.thuctap.services.payment.PaymentServices;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
public class PaymentController {

    @Autowired
    private PaymentServices paymentServices;

    @PostMapping(value = "/add_payment")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponses<Payment> addPayment(@RequestBody String payment) {
        Gson gson = new Gson();
        Payment result = gson.fromJson(payment, Payment.class);
        return paymentServices.addPayment(result);
    }

    @DeleteMapping(value = "/delete_payment")
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponses<Payment> deletePayment(int payment_id) {

        return paymentServices.deletePayment(payment_id);
    }
    // co user
    @GetMapping("/payment_COD")
    public ProductResponses<Order> payment_COD(@RequestParam int order_id) {

        return paymentServices.payment_COD(order_id);
    }

    // ko co user
    @GetMapping("/payment_COD_no_user")
    public CartResponse<HashMap<Integer, Cart>> payment_COD_no_user(HttpSession session) {

        HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("Cart");

        return paymentServices.payment_COD_no_user(cart);
    }

    @GetMapping(value = "/add_payment_link")
    public ResponseEntity<String> addPaymentLink() throws UnsupportedEncodingException {

//        String orderType = "other";
//        long amount = Integer.parseInt(req.getParameter("amount"))*100;
//        String bankCode = req.getParameter("bankCode");

        int amount = 1000000;

        String vnp_TxnRef = Config.getRandomNumber(8);
//        String vnp_IpAddr = Config.getIpAddress(req);
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", Config.vnp_Version);
        vnp_Params.put("vnp_Command", Config.vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_Locale", "vn");
//        vnp_Params.put("vnp_OrderType", orderType);

//        String locate = req.getParameter("language");
//        if (locate != null && !locate.isEmpty()) {
//            vnp_Params.put("vnp_Locale", locate);
//        } else {
//            vnp_Params.put("vnp_Locale", "vn");
//        }
//        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
//        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
//        com.google.gson.JsonObject job = new JsonObject();
//        job.addProperty("code", "00");
//        job.addProperty("message", "success");
//        job.addProperty("data", paymentUrl);
//        Gson gson = new Gson();
//        resp.getWriter().write(gson.toJson(job));

        return new ResponseEntity<>(paymentUrl, HttpStatus.OK);
    }

//    @GetMapping(value = "/payment_info")
//    public ResponseEntity<?> getPaymentInfo(
//            @RequestParam(value = "vnp_Amount") String amount,
//            @RequestParam(value = "vnp_OrderInfo") String order_info,
//            @RequestParam(value = "vnp_BankCode") String bank_code,
//            @RequestParam(value = "vnp_ResponseCode") String response_code,
//            @RequestParam(value = "vnp_CreateDate") String created_at
//
//
//    ) throws ParseException {
//        TransactionStatusDTO<Payment> transactionStatusDTO = new TransactionStatusDTO<>();
//        if (response_code.equals("00")) {
//
//            Payment payment = new Payment();
//            SimpleDateFormat formatter =  new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//            payment.setCreated_at(formatter.parse(created_at));
//            payment.setPayment_method("Thanh toán qua VN pay");
//            payment.setStatus(1);
//
//            paymentServices.addPayment(payment);
//
//            transactionStatusDTO.setStatus(1);
//            transactionStatusDTO.setError("Success");
//            transactionStatusDTO.setData(payment);
//        }
//        else {
//            transactionStatusDTO.setStatus(0);
//            transactionStatusDTO.setError("Fail");
//            transactionStatusDTO.setData(null);
//        }
//        return new ResponseEntity<>(transactionStatusDTO,HttpStatus.OK);
//    }

}

