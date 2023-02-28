package com.dmba.dao;

import com.dmba.dao.proto.UsersOrderProto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users_order")
@NoArgsConstructor
public class UsersOrder {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name = "time_stamp")
    private Timestamp timeStamp;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id")
    private UserAddress userAddress;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "usersOrder", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UsersOrderProductEntity> orderList;

//   @OneToMany(mappedBy = "Product", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToMany(cascade = CascadeType.ALL)
    private List<Product> productList = new ArrayList<>();

    @Column(name = "is_payed")
    private Boolean isPayed;

    @Column(name = "account_total")
    private Float accountTotal;

    public UsersOrder(UsersOrderProto.UsersOrder order) {
        this.uuid = UUID.fromString(order.getUuid().getValue());
        this.timeStamp = new Timestamp(order.getTimeStamp());
        this.accountTotal = order.getAccountTotal();
        this.isPayed = order.getPayed();

        for (UsersOrderProto.UsersOrder.Product  product: order.getOrderList()) {
            Product productEntity = new Product(
                    null, product.getTitle(), product.getPrice(), product.getAmount(), product.getCost());
            this.productList.add(productEntity);
        }

        UserAddress userAddressEntity = new UserAddress(
                null,
                order.getAddress().getZipCode(),
                order.getAddress().getCountry(),
                order.getAddress().getState(),
                order.getAddress().getCity(),
                order.getAddress().getStreet(),
                order.getAddress().getNumberHouse(),
                order.getAddress().getNumberApartment()
        );

        this.userAddress = userAddressEntity;

        User userEntity = User.builder().
                userName(order.getUser().getUserName()).
                age(order.getUser().getAge()).
                role(order.getUser().getRole()).
                build();

        this.user = userEntity;
    }

//    public UsersOrderProto.UsersOrder toUsersOrder() {
//        return UsersOrderProto.UsersOrder.newBuilder()
//                .setUuid(UsersOrderProto.UsersOrder.UUID.newBuilder().setValue(uuid).build())
//                .setTimeStamp(timeStamp)
//                .setAddress(UsersOrderProto.UsersOrder.UserAddress.newBuilder()
//                        .setZipCode(zipCode)
//                        .setCountry(country)
//                        .setState(state)
//                        .setCity(city)
//                        .setStreet(street)
//                        .setNumberHouse(numberHouse)
//                        .setNumberApartment(numberApartment)
//                        .build())
//                .setUser(UsersOrder.User.newBuilder()
//                        .setUserName(userName)
//                        .setAge(age)
//                        .setRole(UsersOrder.Role.forNumber(role))
//                        .build())
//                .build();
//    }
}