package pl.javastart.task.phoneapp;

import pl.javastart.task.contracts.CardContract;
import pl.javastart.task.contracts.MixContract;
import pl.javastart.task.contracts.SubscriptionContract;
import pl.javastart.task.phone.Phone;

public class Main {

    public static void main(String[] args) {
//        Phone phone = new Phone(new CardContract(0.2, .1, .2, 0.5));
//        phone.printAccountState();
//        phone.sendSms();
//        phone.printAccountState();
//        phone.sendSms();
//        phone.printAccountState();
//        phone.sendSms();
//        phone.printAccountState();
//
//        System.out.println();
//        System.out.println("************************************");
//        phone = new Phone(new SubscriptionContract(50));
//        phone.sendSms();
//        phone.printAccountState();
//        phone.sendMms();
//        phone.printAccountState();
//        phone.call(1800);
//        phone.printAccountState();

        System.out.println("*****************************************");
        Phone phone = new Phone(new MixContract(1, 1, 1, 1, .2, .2, .5));
        phone.printAccountState();
//        phone.call(30);
//        phone.printAccountState();
        phone.call(100);
        phone.printAccountState();
        phone.call(100);
        phone.printAccountState();
        phone.sendMms();
        phone.printAccountState();
        phone.sendMms();
        phone.printAccountState();
        phone.sendMms();
        phone.printAccountState();
        phone.sendMms();
        phone.printAccountState();
    }
}
