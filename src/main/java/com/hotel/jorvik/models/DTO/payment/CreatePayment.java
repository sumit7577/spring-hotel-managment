package com.hotel.jorvik.models.DTO.payment;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePayment {

    @SerializedName("paymentType")
    private String paymentType;
    @SerializedName("timeFrom")
    private String timeFrom;
    @SerializedName("timeTo")
    private String timeTo;
    @SerializedName("dateFrom")
    private String dateFrom;
    @SerializedName("dateTo")
    private String dateTo;
    @SerializedName("roomTypeId")
    Integer roomTypeId;
    @SerializedName("entertainmentId")
    Integer entertainmentId;
    @SerializedName("reservationId")
    Integer reservationId;

    public CreatePayment(String paymentType, String dateFrom, String dateTo, int roomTypeId) {
        this.paymentType = paymentType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.roomTypeId = roomTypeId;
    }

    public CreatePayment(String paymentType, String dateFrom, String dateTo, int roomTypeId, Integer reservationId) {
        this.paymentType = paymentType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.roomTypeId = roomTypeId;
        this.reservationId = reservationId;
    }

    public CreatePayment(String paymentType, String dateFrom, String dateTo, String timeFrom, String timeTo, Integer entertainmentId){
        this.paymentType = paymentType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.entertainmentId = entertainmentId;
    }

    public CreatePayment(String paymentType, String dateFrom, String dateTo, String timeFrom, String timeTo, Integer entertainmentId, Integer reservationId){
        this.paymentType = paymentType;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.entertainmentId = entertainmentId;
        this.reservationId = reservationId;
    }
}
