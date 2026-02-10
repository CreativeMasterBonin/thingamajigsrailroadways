package net.rk.railroadways.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.rk.railroadways.Thingamajigsrailroadways;
import net.rk.railroadways.block.TRRBlocks;

public class TRRLanguage extends LanguageProvider {
    public TRRLanguage(PackOutput output, String locale) {
        super(output, Thingamajigsrailroadways.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.thingamajigsrailroadways","Thingamajigs Railroadways");
        this.add(TRRBlocks.RAILROAD_CROSSING_ARM.get(),"Railroad Crossing Gate (Classic Style)");
        this.add(TRRBlocks.RAILROAD_CROSSING_ARM_LIGHTED.get(),"Railroad Crossing Gate (With Lights)");
        this.add(TRRBlocks.CROSSBUCK.get(),"Crossbuck (Customizable)");
        this.add(TRRBlocks.CROSSBUCK_WITH_LADDER.get(),"Crossbuck With Ladder (Customizable)");
        this.add(TRRBlocks.RAILROAD_CROSSING_LIGHTS.get(),"Railroad Crossing Lights");
        this.add(TRRBlocks.BIG_RAILROAD_CROSSING_LIGHTS.get(),"Railroad Crossing Lights (Big Lights)");
        this.add(TRRBlocks.ELECTRONIC_BELL_TYPE_1.get(),"Railroad Crossing E-Bell (Type 1)");
        this.add(TRRBlocks.ELECTRONIC_BELL_TYPE_2.get(),"Railroad Crossing E-Bell (Type 2)");
        this.add(TRRBlocks.ELECTRONIC_BELL_TYPE_3.get(),"Railroad Crossing E-Bell (Type 3)");
        this.add(TRRBlocks.ELECTRONIC_BELL_TYPE_4.get(),"Railroad Crossing E-Bell (Type 4)");
        this.add(TRRBlocks.ELECTRONIC_BELL_TYPE_5.get(),"Railroad Crossing E-Bell (Type 5)");
        this.add(TRRBlocks.MECHANICAL_BELL_TYPE_1.get(),"Railroad Crossing Mechanical Bell (Type 1)");
        this.add(TRRBlocks.MECHANICAL_BELL_TYPE_2.get(),"Railroad Crossing Mechanical Bell (Type 2)");
        this.add(TRRBlocks.CLICKY_MECHANICAL_BELL.get(),"Railroad Crossing Mechanical Bell (Clicky)");
        this.add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER.get(),"Railroad Crossing Cantilever");
        this.add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END.get(),"Railroad Crossing Cantilever End");
        this.add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_LIGHTS.get(),"Railroad Crossing Cantilever Lights");
        this.add(TRRBlocks.BIG_RAILROAD_CROSSING_CANTILEVER_LIGHTS.get(),"Railroad Crossing Cantilever Lights (Big Lights)");
        this.add(TRRBlocks.PURPLE_RAIL.get(),"Purple Rail");
        this.add(TRRBlocks.PURPLE_POWERED_RAIL.get(),"Purple Powered Rail");
        this.add(TRRBlocks.PURPLE_DETECTOR_RAIL.get(),"Purple Detector Rail");
        this.add(TRRBlocks.PURPLE_ACTIVATOR_RAIL.get(),"Purple Activator Rail");
        this.add(TRRBlocks.BRITISH_RAILWAY_LIGHTS.get(),"British Railway Lights");
        this.add(TRRBlocks.BRITISH_RAILWAY_ALARM.get(),"Railway Alarm (Yodeler)");
        this.add(TRRBlocks.TRI_RAILWAY_LIGHTS.get(),"Railway Lights (Tri)");
        this.add(TRRBlocks.DUAL_RAILWAY_LIGHTS.get(),"Railway Lights (Dual)");
        this.add(TRRBlocks.RR_LADDER_POLE.get(),"Vertical Pole With Ladder (Redstone)");
        this.add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_LADDER.get(),"Railroad Crossing Cantilever End (With Ladder)");
        this.add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE.get(),"Railroad Crossing Cantilever End (No Pole)");
        this.add(TRRBlocks.RAILROAD_CROSSING_CANTILEVER_END_NO_POLE_LADDER.get(),"Railroad Crossing Cantilever End (No Pole With Ladder)");
        this.add(TRRBlocks.POLE_CAP.get(),"Pole Cap");


        this.add("editbox.hint.dynamic_sign_crossbuck","Enter texture resource path");
        this.add("checkbox.title.dynamic_sign_crossbuck","Custom Texture");

        this.add("sign_type.aust_alt", "Australian Alt");
        this.add("sign_type.aust", "Australian");
        this.add("sign_type.canadian", "Canadian");
        this.add("sign_type.cateye", "USA Cateye");
        this.add("sign_type.czech", "Czech");
        this.add("sign_type.finnish", "Finnish");
        this.add("sign_type.german_horz", "German Horizontal");
        this.add("sign_type.german_vert", "German Vertical");
        this.add("sign_type.inverted_cateye", "USA Cateye Inverted");
        this.add("sign_type.japan", "Japan");
        this.add("sign_type.usa", "USA");
        this.add("sign_type.inverted_usa", "USA Inverted");
        this.add("sign_type.rr_ahead", "Crossing Ahead");
        this.add("sign_type.rr_ahead_old", "Crossing Ahead (Old)");
        this.add("sign_type.stop_when_red_lights_show","Stop When Red Lights Show");
        this.add("sign_type.unset", "Unset Type");
        this.add("sign_type.custom", "Custom");

        this.add("block.thingamajigsrailroadways.rr_arm.desc","Activates attached vertical redstone components and gate will lower with redstone signal. Gate will rise without redstone signal.");
        this.add("container.thingamajigsrailroadways.rr_arm.title","Railroad Crossing Gate Settings");
        this.add("button.thingamajigsrailroadways.dec_gate", "<- Length");
        this.add("button.thingamajigsrailroadways.inc_gate", "Length ->");
        this.add("button.thingamajigsrailroadways.dec_gate_off", "<- L Offset");
        this.add("button.thingamajigsrailroadways.inc_gate_off", "L Offset ->");
        this.add("button.thingamajigsrailroadways.dec_gate_rot", "<- Rotation");
        this.add("button.thingamajigsrailroadways.inc_gate_rot", "Rotation ->");
        this.add("container.thingamajigsrailroadways.rr_lights.title", "Railroad Crossing Lights Settings");
        this.add("slider.thingamajigsrailroadways.front_left_light_rot", "Front Left Rot: ");
        this.add("slider.thingamajigsrailroadways.front_right_light_rot", "Front Right Rot: ");
        this.add("slider.thingamajigsrailroadways.back_left_light_rot", "Back Left Rot: ");
        this.add("slider.thingamajigsrailroadways.back_right_light_rot", "Back Right Rot: ");
        this.add("button.thingamajigsrailroadways.update_rotation", "Update Rotations");
        this.add("title.thingamajigsrailroadways.lights_screen_data", "FL: %s FR: %s BL: %s BR: %s");
        this.add("checkbox.thingamajigsrailroadways.front_check", "Front Lights");
        this.add("checkbox.thingamajigsrailroadways.back_check", "Back Lights");
        this.add("container.thingamajigsrailroadways.dynamic_sign.title", "Dynamic Sign Settings");
        this.add("container.thingamajigsrailroadways.dynamic_sign.custom_on", "Custom Texture Mode Is On");
        this.add("container.thingamajigsrailroadways.dynamic_sign.custom_off", "Custom Texture Mode Is Off");
        this.add("container.thingamajigsrailroadways.dynamic_sign.sign_type", "Sign Type: %s");
        this.add("button.thingamajigsrailroadways.update_block", "Update Sign");
        this.add("button.thingamajigsrailroadways.dec_sign_type", "<- Type");
        this.add("button.thingamajigsrailroadways.inc_sign_type", "Type ->");
        this.add("button.thingamajigsrailroadways.use_custom_texture", "Use Custom Texture");
        this.add("container.thingamajigsrailroadways.railway_lights.title", "Railway Lights Settings");

        this.add("thingamajigsrailroadways.subtitle.ebell_ring","E-Bell Rings");
        this.add("thingamajigsrailroadways.subtitle.clicky_mechanical_bell_ring","Mechanical Bell Rings");

        this.add("block.thingamajigsrailroadways.purple_powered_rail.desc","Gives a super boost to minecarts");
        this.add("block.thingamajigsrailroadways.purple_activator_rail.desc","Hurts monsters in minecarts above when powered");
        this.add("block.thingamajigsrailroadways.purple_rail.desc","Suspends in mid-air");
        this.add("block.thingamajigsrailroadways.purple_detector_rail.desc","Makes a sound when passed over, and quicker response time");
        this.add("block.thingamajigsrailroadways.crossbuck.desc", "Activates attached vertical redstone components if powered below. Can be rotated, and can choose sign texture.");
        this.add("block.thingamajigsrailroadways.rr_lights.desc", "Activates attached vertical redstone components if powered below. Lights can be rotated individually, as well as the base rotation.");
        this.add("block.thingamajigsrailroadways.railroad_crossing_cantilever.desc", "Supports redstone on top surface");
        this.add("block.thingamajigsrailroadways.railroad_crossing_cantilever_end.desc", "Supports redstone on top surface, and bells");
        this.add("block.thingamajigsrailroadways.railroad_crossing_cantilever_lights.desc", "Supports redstone on top surface, and is controlled by redstone on top. Lights can be rotated individually, as well as the base rotation. Cantilever matches direction placement.");
        this.add("block.thingamajigsrailroadways.pole_cap.desc","Tops off those poles nicely, and spreads redstone power around itself when the block below it is powered with vertical redstone power");
        this.add(TRRTag.CANTILEVER_HELD_ITEMS,"Cantilever Held Items");
    }
}
