package mzc.app.annotations;

import lombok.Getter;
import lombok.Setter;
import mzc.app.annotation.EqualCheck;
import mzc.app.model.BaseModel;
import org.junit.jupiter.api.Test;

public class EqualCheckTest {
    @Getter @Setter
    static class TestModel extends BaseModel {
        @EqualCheck
        private String name = "";

        @EqualCheck
        private String phone = "";

        // not checked
        private String address = "";

        public TestModel(String name, String phone, String address) {
            this.name = name;
            this.phone = phone;
            this.address = address;
        }
    }

    @Test
    public void testCheck() {
        TestModel model1 = new TestModel("name", "phone", "address");
        TestModel model2 = new TestModel("name", "phone", "address2");
        TestModel model3 = new TestModel("name", "phone3", "address");

        assert model1.equals(model2);
        assert !model2.equals(model3);
        assert !model3.equals(model1);
    }
}
