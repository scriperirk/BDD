package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.TransferMoney;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
      open("http://localhost:9999");
      var loginPage = new LoginPage();
//    var loginPage = open("http://localhost:9999", LoginPage.class);
      var authInfo = DataHelper.getAuthInfo();
      var verificationPage = loginPage.validLogin(authInfo);
      var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
      verificationPage.validVerify(verificationCode);
    }

    @Test
    //Переводим с 0002 на 0001
    void transferMoneyToCard1FromCard2() {
        int amount = 8000;

        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var dashboardPage = new DashboardPage();
        // Запомнить текущий баланс
        int currentBalance[] = {
                dashboardPage.getCardBalance(0),
                dashboardPage.getCardBalance(1)
        };
        dashboardPage.card1Balance();

        var transferMoney = new TransferMoney();
        transferMoney.card1Balance(amount);

        int expected1 = currentBalance[0] + amount;
        int expected2 = currentBalance[1] - amount;

        int actual1 = dashboardPage.getCardBalance(0);
        int actual2 = dashboardPage.getCardBalance(1);

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test

    //Переводим с 0001 на 0002
    void transferMoneyToCard2FromCard1() {
        int amount = 3000;

        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

        var dashboardPage = new DashboardPage();
        // Запомнить текущий баланс
        int currentBalance[] = {
                dashboardPage.getCardBalance(0),
                dashboardPage.getCardBalance(1)
        };

        dashboardPage.card2Balance();

        var transferMoney = new TransferMoney();
        transferMoney.card2Balance(amount);

        int expected1 = currentBalance[0] - amount;
        int expected2 = currentBalance[1] + amount;

        int actual1 = dashboardPage.getCardBalance(0);
        int actual2 = dashboardPage.getCardBalance(1);

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

}

