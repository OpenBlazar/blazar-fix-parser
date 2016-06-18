package com.blazarquant.bfp.services.payment;

import com.blazarquant.bfp.common.PathUtils;
import com.blazarquant.bfp.core.payments.SubscriptionPlan;
import com.blazarquant.bfp.core.payments.exception.PaymentException;
import com.blazarquant.bfp.core.payments.paypal.PayPalSubscriptionService;
import com.blazarquant.bfp.core.security.util.SettingsManager;
import com.blazarquant.bfp.data.user.UserAddress;
import com.paypal.base.rest.PayPalRESTException;

import java.io.File;
import java.io.IOException;

/**
 * @author Wojciech Zankowski
 */
public class PaymentServiceImpl implements PaymentService {
    
    private final PayPalSubscriptionService payPalSubscriptionService;

    public PaymentServiceImpl() throws PayPalRESTException, IOException {
        payPalSubscriptionService = new PayPalSubscriptionService(
                new File(
                        PathUtils.joinPath(SettingsManager.getInstance().getPathResolver().getAppDirectory(), "config", "paypal"),
                        "config.properties"
                ));
    }

    @Override
    public String subscribe(SubscriptionPlan subscriptionPlan, UserAddress userAddress) throws PaymentException {
        return payPalSubscriptionService.doSubscription(subscriptionPlan, userAddress);
    }

    @Override
    public SubscriptionPlan confirmPayment(String paymentToken) throws PaymentException {
        return payPalSubscriptionService.confirmSubscription(paymentToken);
    }
}
