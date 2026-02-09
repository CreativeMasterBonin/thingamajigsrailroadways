Thingamajigs 2: Railroadways
---

This is an addon mod for Thingamajigs 2.

Railroadways adds railroad/railway objects used to protect cars and to make your rails safer.

- Purple Rail type track is faster than normal track and can float in midair!
- Purple Powered Rail speeds up trains faster than normal powered rails
- Purple Detector Rail makes sounds when passed over
- Purple Activator Rail attacks enemies who pass over it in minecarts, instead of ejecting them from carts

Railroad Crossing Components are added as well as track in this mod. Different countries' types of equipment and signage are included by default.

Try and mix and match gates, lights, signage and cantilever parts to make the ultimate crossing, or something simple if you want.

Always start with Vertical Redstone Poles or Gates so that the entire signal can be powered from bottom to top.
Cantilevers can be powered by placing redstone on top of them, connecting them to the top of the cantilever end, which should be placed above your crossbucks.

> The order of placement is always:
> <br>
> <br><i> Base (VR Pole/Gate) -> any combo of lights -> crossbuck -> cantilever end -> bell/cap</i>
> <br>
> <br> This ensures proper functionality of the entire crossing system

Placing normal redstone (about 2 pieces, different sides) next to the gate should ensure it will work every time (as there is a redstone bug that is unfixable, we'll blame it on quasi-connectivity).
<br><br><br>
Enjoy the mod!

<br><br>
Contributing? Easy steps ahead.

Just clone and open the project in your IDE of choice, and let gradle setup everything for you. Then run `build` to get a copy of the mod to test in your `build/libs` directory.

To run a test of MC with the mod and dependencies, run the application `Client`, and not the gradle `runClient` as it allows hotswapping when wanted. You need to run those tasks in `Debug` mode to allow advanced changes such as hotswapping.

The `build.gradle` file has a line in it called `jvmArgument("-XX:+AllowEnhancedClassRedefinition")` which enables hotswapping when using a `jbr-XX` java version.
<br><br><br><br>
NOT AN OFFICIAL MINECRAFT PRODUCT. NOT AFFILIATED WITH MOJANG STUDIOS OR MICROSOFT.