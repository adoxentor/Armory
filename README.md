TC-Armory
=========

Armors Mod for Minecraft

# Recommended:
SmithsModding - Weaponry
  -> Adds the TinkersConstruct Materials to Armory

# Requirements:
   *  Minecraft 1.7.10
   *  Minecraft Forge for 1.7.10 (Officialy supported version: 1.7.10-10.13.3.1420)


Installation
============
# Users:
  1) Download all the Dependencies and install those.
  2) Drop Armory in the mods folder.
  
# Modders (JetBrains IDEA):
  1) Fork this Repository
  2) Download the fork
  3) Run on a commandline: 
      1) gradlew setupDecompWorkspace
      2) gradlew copyChicken
      3) gradlew idea
  4) Inside IDEA:
      1) set ~/resources as 'Resources Root'
      2) set ~/src/api as 'Sources Root'
      3) Open the module settings and go to Module Dependencies
      4) Remove the extract options from all of ChickenBones mod: (CCC CCL NEI)
      5) Set teh Scope off al of ChickenBones mods to provided.
