package com.ruanyun.chezhiyi.commonlib.hxchat.domain;

import com.ruanyun.chezhiyi.commonlib.R;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojicon.Type;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EmojiconExampleGroupData {
    
    private static int[] icons = new int[]{
        R.drawable.icon_002_cover,  
        R.drawable.icon_007_cover,  
        R.drawable.icon_010_cover,  
        R.drawable.icon_012_cover,  
        R.drawable.icon_013_cover,  
        R.drawable.icon_018_cover,  
        R.drawable.icon_019_cover,  
        R.drawable.icon_020_cover,  
        R.drawable.icon_021_cover,  
        R.drawable.icon_022_cover,  
        R.drawable.icon_024_cover,  
        R.drawable.icon_027_cover,  
        R.drawable.icon_029_cover,  
        R.drawable.icon_030_cover,  
        R.drawable.icon_035_cover,  
        R.drawable.icon_040_cover,  
    };
    
    private static int[] bigIcons = new int[]{
        R.drawable.icon_002,  
        R.drawable.icon_007,  
        R.drawable.icon_010,  
        R.drawable.icon_012,  
        R.drawable.icon_013,  
        R.drawable.icon_018,  
        R.drawable.icon_019,  
        R.drawable.icon_020,  
        R.drawable.icon_021,  
        R.drawable.icon_022,  
        R.drawable.icon_024,  
        R.drawable.icon_027,  
        R.drawable.icon_029,  
        R.drawable.icon_030,  
        R.drawable.icon_035,  
        R.drawable.icon_040,  
    };
    
    public static  Map<String,EaseEmojicon> EMOJICON_MAP=new HashMap<>();
    private static final EaseEmojiconGroupEntity DATA = createData();
    
    private static EaseEmojiconGroupEntity createData(){
        EaseEmojiconGroupEntity emojiconGroupEntity = new EaseEmojiconGroupEntity();
        EaseEmojicon[] datas = new EaseEmojicon[icons.length];
        for(int i = 0; i < icons.length; i++){
            datas[i] = new EaseEmojicon(icons[i], null, Type.BIG_EXPRESSION);
            datas[i].setBigIcon(bigIcons[i]);
            //you can replace this to any you want
            datas[i].setName("表情"+ (i+1));
            datas[i].setIdentityCode("em"+ (1000+i+1));
        }
        emojiconGroupEntity.setEmojiconList(Arrays.asList(datas));
        emojiconGroupEntity.setIcon(R.drawable.ee_2);
        emojiconGroupEntity.setType(Type.BIG_EXPRESSION);
        initEmojiMap(emojiconGroupEntity);
        return emojiconGroupEntity;
    }
    private static void initEmojiMap(EaseEmojiconGroupEntity entity){
        for(EaseEmojicon easeEmojicon:entity.getEmojiconList()){
            EMOJICON_MAP.put(String.format(REG,easeEmojicon.getName()),easeEmojicon);
        }
    }
   static final String REG="[%s]";
    public static String getIdentiyCode(String name){
        EaseEmojicon easeEmojicon=EMOJICON_MAP.get(name);
        return easeEmojicon==null?"":easeEmojicon.getIdentityCode();
    }
    
    public static EaseEmojiconGroupEntity getData(){
        return DATA;
    }
}
