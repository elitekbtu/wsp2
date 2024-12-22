package users.employees;

import java.util.List;

public class TechSupportSpecialist extends Employee {
    public TechSupportSpecialist(String id, String fullname, String email, String password) {
        super(id, fullname, email, password);
    }

    public TechSupportSpecialist() {
        super();
    }

    public List<TechSupportOrder> getOrders() {
        return null;
    }

    public void acceptOrder(TechSupportOrder order) {
        order.setAccepted(true);
    }

    void rejectOrder(TechSupportOrder order) {
        order.setAccepted(false);
    }
}
