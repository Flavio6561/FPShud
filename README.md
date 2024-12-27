## FPShud,
a client side mod that displays FPS with high precision on a customizable HUD

### Mod features:

- **`Main FPS`** is the main FPS value, regulated by the **`Main Update Interval`**
- **`Avererage FPS`** is the average FPS value from all the values took in **`Average Update Interval`**
- **`Max FPS`** is the maximum FPS value from all the values took in **`Min Update Interval`**
- **`Min FPS`** is the minimum FPS value from all the values took in **`Max Update Interval`**

>![FPShud_DefaultSettings](https://raw.githubusercontent.com/Flavio6561/Gallery/refs/heads/main/FPShud_DefaultSettings.gif)
> `FPShud with default settings`

**HUD Features:**

- **`Toggle HUD`** toggles the entire HUD
- **`Show FPS`** toggles the **`Main FPS`** field
- **`Show Average FPS`** toggles the **`Average FPS`** field
- **`Show Max FPS`** toggles the **`Max FPS`** field
- **`Show Min FPS`** toggles the **`Min FPS`** field
- **`Text Color`** changes the color of the entire HUD
- **`Shadow`** enables or disables the text shadow on the HUD
- **`X Position`** changes the X position of the HUD on the screen
- **`Y Position`** changes the Y position of the HUD on the screen
- **`Before FPS Text`** changes the text displayed before the **`Main FPS`** value. (Also available for Avr, Max and Min)
- **`After FPS Text`** changes the text displayed after the **`Main FPS`** value. (Also available for Avr, Max and Min)
- **`Separator`** changes the text that separates the 4 fields

**Advanced features:**

- **`Polling Rate`** indicates how frequently the mod should poll FPS. `The lowest 'Update Interval' value must be higher than the 'Polling Rate'`
- **`Precision`** indicates how many points of precision the mod should display on the HUD

### ModMenu config:

- GUI config is accessible from the ModMenu mods list
> The config screen is divided into 2 categories:
> - HUD options:
>![FPShud_ConfigScreen1](https://raw.githubusercontent.com/Flavio6561/Gallery/refs/heads/main/FPShud_ConfigScreen1.png)
> - Advanced:
>![FPShud_ConfigScreen2](https://raw.githubusercontent.com/Flavio6561/Gallery/refs/heads/main/FPShud_ConfigScreen2.png)

### Why FPShud?

**Highly precise:**
> FPShud uses time elapsed from frame to frame for the FPS value calculation;
> This method of sampling elevates precision and customization, permitting millisecond precise calculations and FPS displaying  
> 
> FPShud works with a **`Polling Rate`** algorithm; samples are taken every frame, then smoothen every poll-time and finally processed to make them match the required
> **`Main Update Interval`**, **`Average Update Interval`**, **`Maximum Update Interval`** and **`Minimum Update Interval`** requirements 
> 
> For those reasons, it is not recommended to set `Polling Rate` and `Update Interval` to low values because results may appear very fluctuating

**Highly customizable:**

> You can customize the HUD to match every style:  
> 
>![FPShud_Custom2](https://raw.githubusercontent.com/Flavio6561/Gallery/refs/heads/main/FPShud_Custom2.gif)
> `only showing Avr and Max, modified 'Before Text' and 'Separator Text', changed 'Text Color', removed 'Shadow', changed 'Precision' and modified 'Update Intervals'`

### Other:

> If you plan using ModMenu install ClothConfigAPI to access the config screen

> Don't want ModMenu? The config file is accessible and editable in your config directory, changes apply after a game restart

> Settings will be saved in your config folder and updated at runtime

> The mod is entirely client side, this will make the mod work on servers, but will not affect server interactions

### Links:
> - [GitHub page](https://github.com/Flavio6561/FPShud)
> - [Modrinth page](https://modrinth.com/mod/FPShud)
> - [Changelogs](https://github.com/Flavio6561/FPShud/wiki/Version-changelogs)
> - [Report an issue](https://github.com/Flavio6561/FPShud/issues)