package draw.score;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GuitarStaff {
	private Line staffLine;
    public GuitarStaff() {
		super();
		
		// TODO Auto-generated constructor stub
	}
    
	public void draw(Group box, int rowNumber) {

		if(rowNumber<1) {
			int i=358;
			Text t = new Text(105, i, "T");
			t.setFont(Font.font("Verdana", 24));
			t.setFill(Color.BLACK);
			i=i+21;
			Text a = new Text(105, i, "A");
			a.setFont(Font.font("Verdana", 24));
			a.setFill(Color.BLACK);
			i=i+20;
			Text b = new Text(105, i, "B");
			
			b.setFont(Font.font("Verdana", 24));
			b.setFill(Color.BLACK);
			box.getChildren().add(t);
			box.getChildren().add(a);
			box.getChildren().add(b);
		}
		else {
			
		
			Text t = new Text(105, 150*rowNumber+358, "T");
			t.setFont(Font.font("Verdana", 25));
			t.setFill(Color.BLACK);
			
			Text a = new Text(105, 150*rowNumber+379, "A");
			a.setFont(Font.font("Verdana", 25));
			a.setFill(Color.BLACK);
		
			Text b = new Text(105, 150.0*rowNumber+399, "B");
			b.setFont(Font.font("Verdana", 25));
			b.setFill(Color.BLACK);
			box.getChildren().add(t);
			box.getChildren().add(a);
			box.getChildren().add(b);
		}
	
		for (int j=0;j<6;j++) {
			
			staffLine = new Line(
					100.0,
					150.0*rowNumber+400.0-13.0*j,
					900.0,
					150.0*rowNumber+400.0-13.0*j);

			box.getChildren().add(staffLine);
			}
		
    }

}

