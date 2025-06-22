// BuilderPatternExample.java

class Computer {
    // Required parameters
    private String CPU;
    private String RAM;

    // Optional parameters
    private String storage;
    private String graphicsCard;
    private String keyboard;
    private String monitor;

    // Private constructor: only accessible via Builder
    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.keyboard = builder.keyboard;
        this.monitor = builder.monitor;
    }

    // Static Nested Builder Class
    public static class Builder {
        // Required
        private String CPU;
        private String RAM;

        // Optional
        private String storage;
        private String graphicsCard;
        private String keyboard;
        private String monitor;

        public Builder(String CPU, String RAM) {
            this.CPU = CPU;
            this.RAM = RAM;
        }

        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder setGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        public Builder setKeyboard(String keyboard) {
            this.keyboard = keyboard;
            return this;
        }

        public Builder setMonitor(String monitor) {
            this.monitor = monitor;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

    // Display method
    public void showSpecs() {
        System.out.println("CPU: " + CPU);
        System.out.println("RAM: " + RAM);
        System.out.println("Storage: " + (storage != null ? storage : "Not included"));
        System.out.println("Graphics Card: " + (graphicsCard != null ? graphicsCard : "Not included"));
        System.out.println("Keyboard: " + (keyboard != null ? keyboard : "Not included"));
        System.out.println("Monitor: " + (monitor != null ? monitor : "Not included"));
        System.out.println("--------------------------------------------------");
    }
}

public class BuilderPatternExample {
    public static void main(String[] args) {
        // Basic computer
        Computer basicComputer = new Computer.Builder("Intel i3", "4GB").build();
        System.out.println("Basic Computer:");
        basicComputer.showSpecs();

        // Gaming computer
        Computer gamingComputer = new Computer.Builder("Intel i9", "32GB")
                .setGraphicsCard("NVIDIA RTX 4080")
                .setStorage("2TB SSD")
                .setMonitor("4K Gaming Monitor")
                .setKeyboard("Mechanical RGB Keyboard")
                .build();

        System.out.println("Gaming Computer:");
        gamingComputer.showSpecs();

        // Office computer
        Computer officeComputer = new Computer.Builder("Intel i5", "8GB")
                .setStorage("512GB SSD")
                .setMonitor("HD Monitor")
                .build();

        System.out.println("Office Computer:");
        officeComputer.showSpecs();
    }
}
