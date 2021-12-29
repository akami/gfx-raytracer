package at.ac.univie.unet.a01638800.surface;

public class Mesh extends Surface {
    private String name;

    public Mesh(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
