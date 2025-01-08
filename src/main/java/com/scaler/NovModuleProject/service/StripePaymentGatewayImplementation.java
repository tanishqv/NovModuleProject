package com.scaler.NovModuleProject.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentGatewayImplementation implements PaymentService {

    @Override
    public String createPaymentLink(String orderId, Long amount) throws StripeException {
        Stripe.apiKey = "****";

        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency("INR")
                .setUnitAmount(amount)
                .setProductData(
                        PriceCreateParams.ProductData.builder()
                                .setName(orderId).build()
                )
                .build();
        Price price = Price.create(params);

        PaymentLinkCreateParams linkParams = PaymentLinkCreateParams.builder()
                .addLineItem(
                        PaymentLinkCreateParams.LineItem.builder()
                                .setPrice(price.getId())
                                .setQuantity(1L)
                                .build()
                )
                .setAfterCompletion(
                        PaymentLinkCreateParams.AfterCompletion.builder()
                                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                .setRedirect(
                                        PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                .setUrl("https://www.google.com/search?q=payment+done+website")
                                                .build()
                                )
                                .build()
                )
                .build();

        return PaymentLink.create(linkParams).getUrl();
    }
}
