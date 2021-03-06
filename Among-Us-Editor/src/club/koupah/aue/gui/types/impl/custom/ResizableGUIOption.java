package club.koupah.aue.gui.types.impl.custom;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import club.koupah.aue.Editor;
import club.koupah.aue.gui.types.impl.CheckboxSetting;

public class ResizableGUIOption extends CheckboxSetting {
	
	
	public ResizableGUIOption(JLabel label, final JCheckBox component, int settingIndex) {
		super(label, component, settingIndex);
		component.setText("Off");
		Editor.getInstance().setAlwaysOnTop(Editor.getInstance().configManager.getAOT());
		component.setSelected(Editor.getInstance().configManager.getAOT());
		component.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Editor.getInstance().setResizable(((JCheckBox)component).isSelected());
				Editor.getInstance().configManager.setCustomResolution(Editor.getInstance().isResizable());
				if (((JCheckBox)component).isSelected()) 
					Editor.getInstance().updateWidth(Editor.getInstance().configManager.getCustomWidth());
				else
				Editor.getInstance().updateWidth(445);
				Editor.getInstance().configManager.saveConfig();

				ResizableGUIOption.this.updateLabel();
			}
			
		});
	}

	@Override
	public void updateLabel() {
		label.setText(getLabelText() + (((JCheckBox)component).isSelected() ? "On" : "Off"));
	}
	
	@Override
	public void updateComponent() {
		((JCheckBox)component).setSelected(Editor.getInstance().isResizable());
		((JCheckBox)component).setText(((JCheckBox)component).isSelected() ? "On" : "Off");
	}

	@Override
	public String getProperValue() {
		return null;
	}

}
