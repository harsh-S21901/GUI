package Cars;

public class RailroadPostOffice extends RailroadCar {
    private boolean hasSecurityGuard;
    private boolean hasMail;
    private int numberOfMailBox;
    private int numberOfMails;

    public RailroadPostOffice(boolean hasSecurityGuard, boolean hasMail, int numberOfMailBox, int weight) {
        super(weight);
        this.hasSecurityGuard = hasSecurityGuard;
        this.hasMail = hasMail;
        this.numberOfMailBox=numberOfMailBox;
        this.numberOfMails=0;
    }

    public void loadMails(int numberOfMails){
        if(this.numberOfMails + numberOfMails > numberOfMailBox){
            this.numberOfMails=numberOfMailBox;
            System.out.println("Error: Not enough mails to load " + numberOfMails + " mails.");
        } else
        {
            this.numberOfMails += numberOfMails;
            System.out.println(numberOfMails + " mails loaded.");
        }
    }

    public void unloadMail(){
        if(this.numberOfMails == 0){
            System.err.println("No mails found to unload.");
        }else {
            this.numberOfMails = 0;
            System.out.println("Mails unloaded.");
        }
    }

    public int getNumberOfMailBox() {
        return numberOfMailBox;
    }

    public void setNumberOfMailBox(int numberOfMailBox) {
        this.numberOfMailBox = numberOfMailBox;
    }

    public int getNumberOfMails() {
        return numberOfMails;
    }

    public void setNumberOfMails(int numberOfMails) {
        this.numberOfMails = numberOfMails;
    }

    public boolean isHasSecurityGuard() {
        return hasSecurityGuard;
    }

    public void setHasSecurityGuard(boolean hasSecurityGuard) {
        this.hasSecurityGuard = hasSecurityGuard;
    }

    public boolean isHasMail() {
        return hasMail;
    }

    public void setHasMail(boolean hasMail) {
        this.hasMail = hasMail;
    }

    @Override
    public boolean requiresElectricity() {
        return false;
    }

    @Override
    public String getType() {
        return "Railroad Post Office Car";
    }

    @Override
    public String toString() {
        return "RailroadPostOffice{" +
                "id=" + getId()+
                " , hasSecurityGuard=" + hasSecurityGuard +
                ", hasMail=" + hasMail +
                ", numberOfMailBox=" + numberOfMailBox +
                ", numberOfMails=" + numberOfMails +
                '}';
    }
}
