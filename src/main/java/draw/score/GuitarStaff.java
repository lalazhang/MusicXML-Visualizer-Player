package draw.score;
import javafx.scene.Group;
import javafx.scene.shape.Line;

public class GuitarStaff {
	private Line staffLine;
    public GuitarStaff() {
		super();
		
		// TODO Auto-generated constructor stub
	}

	public void draw(Group box, int rowNumber) {
		for (int i=0;i<6;i++) {
			staffLine = new Line(100.0,100.0*rowNumber+250.0-10.0*i,900.0,100.0*rowNumber+250.0-10.0*i);
			box.getChildren().add(staffLine);

			}
    }
}

