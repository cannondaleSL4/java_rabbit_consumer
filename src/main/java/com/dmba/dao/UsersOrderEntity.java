package com.dmba.dao;

import com.dmba.dao.proto.UsersOrderProto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users_order")
public class UsersOrderEntity {

    @Id
    @Column(name = "id", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name = "timestamp")
    private Timestamp timeStamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private UserAddressEntity userAddress;

    @OneToMany(mappedBy = "usersOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> orderList;

    @Column(name = "payed")
    private Boolean payed;

    @Column(name = "account_total")
    private Float accountTotal;

    public UsersOrderEntity(UsersOrderProto.UsersOrder order) {
        this.uuid = UUID.fromString(order.getUuid().getValue());
        this.timeStamp = new Timestamp(order.getTimeStamp());
        this.accountTotal = order.getAccountTotal();
        this.payed = order.getPayed();

        for (UsersOrderProto.UsersOrder.Product  product: order.getOrderList()) {
            ProductEntity productEntity = ProductEntity.builder().
                    title(product.getTitle()).
                    price(product.getPrice()).
                    cost(product.getCost()).
                    amount(product.getAmount()).
                    build();
            this.orderList.add(productEntity);
        }

        UserAddressEntity userAddressEntity = UserAddressEntity.builder().
                state(order.getAddress().getState()).
                zipCode(order.getAddress().getZipCode()).
                city(order.getAddress().getCity()).
                street(order.getAddress().getStreet()).
                country(order.getAddress().getCountry()).
                numberHouse(order.getAddress().getNumberHouse()).
                numberApartment(order.getAddress().getNumberApartment()).
                build();

        this.userAddress = userAddressEntity;

        UserEntity.Role role;

        if (order.getUser().getRoleValue() == 0) {
            role = UserEntity.Role.USER;
        } else {
            role = UserEntity.Role.VIP_USER;
        }

        UserEntity userEntity = UserEntity.builder().
                userName(order.getUser().getUserName()).
                age(order.getUser().getAge()).
                role(role).
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