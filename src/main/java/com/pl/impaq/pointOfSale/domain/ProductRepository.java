package com.pl.impaq.pointOfSale.domain;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> getOptional(BarCode code);

}
