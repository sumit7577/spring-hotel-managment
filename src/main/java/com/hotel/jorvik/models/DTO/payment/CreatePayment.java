package com.hotel.jorvik.models.DTO.payment;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePayment {
    public enum PaymentType {
        ROOM_PAYMENT,
        BICYCLE_PAYMENT,
        KAYAK_PAYMENT,
        TENNIS_PAYMENT,
    }

    @SerializedName("paymentType")
    private PaymentType paymentType;
    @SerializedName("timestampFrom")
    private String timestampFrom;
    @SerializedName("dateFrom")
    private String dateFrom;
    @SerializedName("dateTo")
    private String dateTo;
    @SerializedName("roomTypeId")
    int roomTypeId;

    public CreatePayment(PaymentType paymentType, String timestampFrom){
        this.paymentType = paymentType;
        this.timestampFrom = timestampFrom;
    }

    public CreatePayment(PaymentType paymentType,  String dateFrom, String dateTo, int roomTypeId) {
        this.paymentType = paymentType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.roomTypeId = roomTypeId;
    }
}
