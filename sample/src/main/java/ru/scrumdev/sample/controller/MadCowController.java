package ru.scrumdev.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.scrumdev.sample.server.DealManager;
import ru.scrumdev.sample.shared.Bid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/")
public class MadCowController {

    @Autowired
    protected DealManager dealManager;

    @ResponseBody
    @RequestMapping(value = "bids", method = RequestMethod.GET)
    public List<Bid> getBids() throws IllegalAccessException {
        /*List<Bid> result = new ArrayList<>();

        Bid b1 = new Bid();
        b1.setQty(new Random().nextInt());
        b1.setPrice(new BigDecimal(new Random().nextDouble()));

        result.add(b1);

        Bid b2 = new Bid();
        b2.setQty(new Random().nextInt());
        b2.setPrice(new BigDecimal(new Random().nextDouble()));

        result.add(b2);*/

        return dealManager.getSellBids();
    }
}
