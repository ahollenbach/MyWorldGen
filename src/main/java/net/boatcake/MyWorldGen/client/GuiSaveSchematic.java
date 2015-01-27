package net.boatcake.MyWorldGen.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.boatcake.MyWorldGen.MyWorldGen;
import net.boatcake.MyWorldGen.Schematic;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GuiSaveSchematic extends GuiScreen {
	private GuiButton cancelBtn;
	private GuiTextField fileNameField;
	private GuiButton saveBtn;
	private GuiSlotChestGenTypes slot;

	private GuiButton lockRotationButton;
	private GuiButton generateSpawnersButton;
	private GuiButton fuzzyMatchingButton;
	private GuiButton terrainSmoothingButton;

	public Schematic schematicToSave;

	public GuiSaveSchematic() {
		super();
		// The schematicToSave is filled out for us in PacketHandler
	}

	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		
		fileNameField = new GuiTextField(0, this.fontRendererObj,
				this.width / 2 - 150, 20, 300, 20);
		fileNameField.setMaxStringLength(32767);
		fileNameField.setFocused(true);

		boolean lockRotation, generateSpawners, fuzzyMatching, terrainSmoothing;
		if (schematicToSave == null) {
			lockRotation = false;
			generateSpawners = true;
			fuzzyMatching = false;
			terrainSmoothing = false;
		} else {
			lockRotation = schematicToSave.info.lockRotation;
			generateSpawners = schematicToSave.info.generateSpawners;
			fuzzyMatching = schematicToSave.info.fuzzyMatching;
			terrainSmoothing = schematicToSave.info.terrainSmoothing;
		}
		buttonList.add(lockRotationButton = new GuiButton(2,
				this.width / 2 + 2, 60, 150, 20, I18n
						.format("gui.lockRotation." + lockRotation)));
		buttonList.add(generateSpawnersButton = new GuiButton(3,
				this.width / 2 - 152, 60, 150, 20, I18n
						.format("gui.generateSpawners." + generateSpawners)));
		buttonList.add(fuzzyMatchingButton = new GuiButton(4,
				this.width / 2 + 2, 84, 150, 20, I18n
						.format("gui.fuzzyMatching." + fuzzyMatching)));
		buttonList.add(terrainSmoothingButton = new GuiButton(5,
				this.width / 2 - 152, 84, 150, 20, I18n
						.format("gui.terrainSmoothing." + terrainSmoothing)));

		slot = new GuiSlotChestGenTypes(this.mc, this, this.fontRendererObj,
				this.width / 2 - 152, 108, 150, this.height - 132);
		slot.registerScrollButtons(6, 7);
		
		buttonList.add(saveBtn = new GuiButton(0, this.width / 2 + 2,
				this.height - 20, 150, 20, I18n.format("gui.save")));
		buttonList.add(cancelBtn = new GuiButton(1, this.width / 2 - 152,
				this.height - 20, 150, 20, I18n.format("gui.cancel")));

		updateSaveButton();
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		if (slot == null) {
			// Sometimes, initGui will not have been called yet. I think it's a
			// race condition on my platform that I can't easily fix right now,
			// but this works anyway.
			return;
		}
		drawDefaultBackground();
		slot.drawScreen(par1, par2, par3);
		drawCenteredString(fontRendererObj, I18n.format("gui.filename"),
				this.width / 2, 5, 0xFFFFFF);
		drawCenteredString(fontRendererObj,
				I18n.format("selectWorld.resultFolder") + " "
						+ MyWorldGen.globalSchemDir.getAbsolutePath(),
				this.width / 2, 45, 0xA0A0A0);
		fileNameField.drawTextBox();
		super.drawScreen(par1, par2, par3);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		if (button.id == saveBtn.id && saveBtn.enabled) {
			// Step 5: Now that we have the block data and entity and tile
			// entity data, saving it to a file should be trivial.
			schematicToSave.info.chestType = slot.hooks[slot.selected];
			String name = fileNameField.getText();
			if (!name.contains(".")) {
				name += ".schematic";
			}
			try {
				CompressedStreamTools.writeCompressed(schematicToSave.getNBT(),
						new FileOutputStream(new File(
								MyWorldGen.globalSchemDir, name)));
			} catch (Exception exc) {
				// File does't exist/can't be written
				// TODO: make this nicer?
				mc.displayGuiScreen(new GuiErrorScreen(
						exc.getClass().getName(), exc.getLocalizedMessage()));
				exc.printStackTrace();
				return;
			}
			mc.displayGuiScreen(null);
		} else if (button.id == cancelBtn.id) {
			mc.displayGuiScreen(null);
		} else if (button.id == lockRotationButton.id) {
			if (schematicToSave != null) {
				schematicToSave.info.lockRotation = !schematicToSave.info.lockRotation;
				lockRotationButton.displayString = I18n
						.format("gui.lockRotation."
								+ schematicToSave.info.lockRotation);
			}
		} else if (button.id == generateSpawnersButton.id) {
			if (schematicToSave != null) {
				schematicToSave.info.generateSpawners = !schematicToSave.info.generateSpawners;
				generateSpawnersButton.displayString = I18n
						.format("gui.generateSpawners."
								+ schematicToSave.info.generateSpawners);
			}
		} else if (button.id == fuzzyMatchingButton.id) {
			if (schematicToSave != null) {
				schematicToSave.info.fuzzyMatching = !schematicToSave.info.fuzzyMatching;
				fuzzyMatchingButton.displayString = I18n
						.format("gui.fuzzyMatching."
								+ schematicToSave.info.fuzzyMatching);
			}
		} else if (button.id == terrainSmoothingButton.id) {
			if (schematicToSave != null) {
				schematicToSave.info.terrainSmoothing = !schematicToSave.info.terrainSmoothing;
				terrainSmoothingButton.displayString = I18n
						.format("gui.terrainSmoothing."
								+ schematicToSave.info.terrainSmoothing);
			}
		} else {
			slot.actionPerformed(button);
		}
	}

	@Override
	protected void keyTyped(char character, int keycode) throws IOException {
		fileNameField.textboxKeyTyped(character, keycode);
		updateSaveButton();

		switch (keycode) {
		case Keyboard.KEY_RETURN:
		case Keyboard.KEY_NUMPADENTER:
			this.actionPerformed(saveBtn);
			break;
		case Keyboard.KEY_ESCAPE:
			this.actionPerformed(cancelBtn);
			break;
		default:
			break;
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void handleMouseInput() throws IOException {
		if (slot == null) {
			return;
		}
		super.handleMouseInput();
		slot.handleMouseInput();
	}

	public void updateSaveButton() {
		// Call this every so often to make sure we have a valid file name and a
		// valid schematic
		saveBtn.enabled = fileNameField.getText().trim().length() > 0
				&& schematicToSave != null && schematicToSave.entities != null
				&& schematicToSave.tileEntities != null;
	}

	@Override
	public void updateScreen() {
		if (fileNameField == null) {
			return;
		}
		super.updateScreen();
		fileNameField.updateCursorCounter();
	}
}
