package draw.score;

import javafx.scene.Group;
import javafx.scene.shape.Line;

public class DrumStaff {
	
	private Line staffLine;
    public DrumStaff() {
		super();
		
		// TODO Auto-generated constructor stub
	}

	public void draw(Group box, int rowNumber) {
		for (int i=0;i<5;i++) {
			staffLine = new Line(100.0,90.0*rowNumber+250.0-10.0*i,900.0,90.0*rowNumber+250.0-10.0*i);

			box.getChildren().add(staffLine);

			}
    }
}
