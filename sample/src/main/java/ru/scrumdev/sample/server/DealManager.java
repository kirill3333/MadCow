package ru.scrumdev.sample.server;

import org.springframework.stereotype.Service;
import ru.scrumdev.sample.shared.Bid;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DealManager {
    public List<Bid> getSellBids() {
        List<Bid> result = new ArrayList<>();

        Bid b1 = new Bid();
        b1.setQty(new Random().nextInt());
        b1.setPrice(new BigDecimal(new Random().nextDouble()));

        result.add(b1);

        Bid b2 = new Bid();
        b2.setQty(new Random().nextInt());
        b2.setPrice(new BigDecimal(new Random().nextDouble()));

        result.add(b2);

        return result;
    }

    public List<Bid> getBuyBids() {
        return null;
    }
}
