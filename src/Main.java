import java.util.Arrays;
import java.util.List;

abstract class Account {
    private String name;
    private static int accountNumber=0;
    private double amount;

    public Account(String name, double amount) {
        this.name = name;
        accountNumber++;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
    public double insertMoney(double money){
        return amount+=money;
    }
    public void extendMoney(double money){
        if(amount>=money){
            amount-=money;
        }else{
            System.out.println("not enough money");
        }
    }
}
class NonInterestCheckingAccount extends Account {

    public NonInterestCheckingAccount(String name, double amount) {
        super(name, amount);
    }
}

class InterestCheckingAccount extends Account implements InterestBearingAccount {

    public final static double KAMATA=1.03;
    public InterestCheckingAccount(String name, double amount) {
        super(name, amount);
    }

    @Override
    public void addInterest() {
        insertMoney(getAmount()*KAMATA);
    }
}

class PlatinumCheckingAccount extends InterestCheckingAccount implements InterestBearingAccount{


    public PlatinumCheckingAccount(String name, double amount) {
        super(name, amount);
    }

    @Override
    public void addInterest() {
        insertMoney(getAmount()*KAMATA*2);
    }
}









//Во Bank чува листа од сите видови сметки, вклучувајќи сметки
// за штедење и за трошење, некои од нив подложни на камата,
// а некои не. Во Bank постои метод totalAssets кој ја враќа
// сумата на состојбата на сите сметки. Исто така содржи метод
// addInterest кој го повикува методот addInterest на сите сметки кои се подложни на камата.

class Bank{
    private Account [] accounts;
    private int totalAcc;
    private int max;

    public Bank(int max) {
        this.totalAcc = 0;
        this.max = max;
        accounts = new Account[max];
    }
    public void addAccount(Account account) {
        if (totalAcc == accounts.length) {
            accounts = Arrays.copyOf(accounts, max * 2);
        }
        accounts[totalAcc++] = account;
    }


    public double totalAssets(){

        double sum=0;
        for (Account acc:accounts){
            sum+=acc.getAmount();
        }
        return sum;
    }
    public void addInterest(){
        for (Account acc:accounts){
            if(acc instanceof InterestBearingAccount){
                InterestBearingAccount iba = (InterestBearingAccount) acc;
                iba.addInterest();
            }
        }
    }
}


