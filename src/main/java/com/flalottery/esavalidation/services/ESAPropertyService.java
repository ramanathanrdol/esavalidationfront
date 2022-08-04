package com.flalottery.esavalidation.services;

import com.flalottery.esavalidation.model.CommonMessages;
import com.flalottery.esavalidation.model.PromotionPropertiesDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ESAPropertyService{
    final Logger logger = LoggerFactory.getLogger(ESAPropertyService.class);
    @Autowired
    Environment env;
    @Autowired
    PromotionPropertiesDetail promotionPropertiesDetail;
    @Autowired
    CommonMessages commonMessages;
    String[] promotions;

    HashMap<String, PromotionPropertiesDetail> promotionsProperties = new HashMap<String, PromotionPropertiesDetail>();

    public String[] getPromotions() {
        promotions = env.getProperty("esa.validation.promotions.name", String[].class);
        return promotions;
    }
@Cacheable("esaProperties")
    public void getEsaPropertiesDetail() {
        String[] promotions = getPromotions();

        promotionPropertiesDetail.setCommonMessages(this.getCommonMessages());
        for (String promotion : promotions) {
            promotionPropertiesDetail.setPromotionName(promotion);
            promotionPropertiesDetail.setEsaBonusPlayURI(env.getProperty("esa.validation.bonusplay.uri"));
            promotionPropertiesDetail.setEsaWebsiteURI(env.getProperty("esa.validation.website.uri"));
            promotionPropertiesDetail.setDrawURL(env.getProperty("esa.validation.draw.url"));
            promotionPropertiesDetail.setScratchOffURL(env.getProperty("esa.validation.scratchoff.url"));
            promotionPropertiesDetail.setQuickTicketURL(env.getProperty("esa.validation.quickticket.url"));
            promotionPropertiesDetail.setDrawAPIKeyName(env.getProperty("esa.validation.draw.apikey.name"));
            promotionPropertiesDetail.setDrawAPIKeyValue(env.getProperty("esa.validation.draw.apikey.value"));
            promotionPropertiesDetail.setScratchOffAPIKeyName(env.getProperty("esa.validation.scratchoff.apikey.name"));
            promotionPropertiesDetail.setScratchOffAPIKeyValue(env.getProperty("esa.validation.scratchoff.apikey.value"));
            promotionPropertiesDetail.setScratchOffMethodName(env.getProperty("esa.validation.scratchoff.method.name"));
            promotionPropertiesDetail.setScratchOffMethodManual(env.getProperty("esa.validation.scratchoff.method.manual"));
            promotionPropertiesDetail.setQuickTicketAPIKeyName(env.getProperty("esa.validation.quickticket.apikey.name"));
            promotionPropertiesDetail.setQuickTicketAPIKeyValue(env.getProperty("esa.validation.quickticket.apikey.value"));
            promotionPropertiesDetail.setQuickTicketMethodName(env.getProperty("esa.validation.quickticket.method.name"));
            promotionPropertiesDetail.setQuickTicketMethodManual(env.getProperty("esa.validation.quickticket.method.manual"));
            promotionPropertiesDetail.getTicketFormats().setDrawWithDash(env.getProperty("esa.validation.ticket.format.draw.withdash"));
            promotionPropertiesDetail.getTicketFormats().setDrawWithOutDash(env.getProperty("esa.validation.ticket.format.draw.withoutdash"));
            promotionPropertiesDetail.getTicketFormats().setScratchOffPDF417(env.getProperty("esa.validation.ticket.format.scratchoff.pdf417"));
            promotionPropertiesDetail.getTicketFormats().setScratchOffInterleaved(env.getProperty("esa.validation.ticket.fromat.scratchoff.interleaved"));
            promotionPropertiesDetail.getTicketFormats().setScratchOffInterleavedPin(env.getProperty("esa.validation.ticket.fromat.scratchoff.interleaved.pin"));
            promotionPropertiesDetail.getTicketFormats().setQuickTicketFormat(env.getProperty("esa.validation.ticket.fromat.quickticket"));
            promotionPropertiesDetail.getProduct().setProductCode(env.getProperty("esa.validation." + promotion + ".product.code"));
            promotionPropertiesDetail.getProduct().setProductName(env.getProperty("esa.validation." + promotion + ".product.name"));
            promotionPropertiesDetail.getProduct().setDayMaxCount(env.getProperty("esa.validation." + promotion + ".entry.maxcount.perday"));
            promotionPropertiesDetail.getProduct().setInvalidProductErrorMsg(env.getProperty("esa.validation." + promotion + ".error.message.invalid"));
            promotionsProperties.put(promotion, promotionPropertiesDetail);
        }
       // return promotionsProperties;
    }

    public CommonMessages getCommonMessages() {
        commonMessages.setCommonMessage(env.getProperty("esa.validation.error.message.common"));
        commonMessages.setDayMaxMessage(env.getProperty("esa.validation.error.message.daymax"));
        commonMessages.setInvalidTicketMessage(env.getProperty("esa.validation.error.message.invalid"));
        commonMessages.setInvalidPinMessage(env.getProperty("esa.validation.error.message.invalidpin"));
        return commonMessages;
    }

    public PromotionPropertiesDetail getPromotionPropertiesDetail(String promotion) {
        return getPromotionsProperties().get(promotion);
    }

    public HashMap<String, PromotionPropertiesDetail> getPromotionsProperties() {
        return promotionsProperties;
    }

}
