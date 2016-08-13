package com.Adoxentor.ElytraCore;


import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;


import static org.objectweb.asm.Opcodes.*;

import java.util.Arrays;

/**
 * Created by Iddo on 6/17/2016.
 */
public class ElytraTransformer implements IClassTransformer {

    private static final String[] classesBeingTransformed =
            {
                    "net.minecraft.entity.EntityLivingBase",
                    "net.minecraft.client.entity.EntityPlayerSP",
                    "net.minecraft.network.NetHandlerPlayServer"
            };

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        boolean isObfuscated = !name.equals(transformedName);
        int index = Arrays.asList(classesBeingTransformed).indexOf(transformedName);
        return index != -1 ? transform(index, basicClass, isObfuscated) : basicClass;
    }
    private static byte[] transform(int index, byte[] classBeingTransformed, boolean isObfuscated) {
        ElytraCore.logger.info("Transforming: " + classesBeingTransformed[index]);
        try
        {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(classBeingTransformed);
            classReader.accept(classNode, 0);

            switch(index)
            {
                case 0:
                    transformElytraCheckEntityLivingBase(classNode, isObfuscated);
                    break;
                case 1:
                    transformElytraCheckEntityPlayerSP(classNode,isObfuscated);
                    break;
                case 2:
                    transformElytraCheckEntityNetHandlerPlayServer(classNode,isObfuscated);
                    break;
            }

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return classBeingTransformed;
    }

    private static void transformElytraCheckEntityLivingBase(ClassNode classNode,boolean isObfuscated){
        String method_name = isObfuscated ? "r" : "updateElytra";
        String method_dec = isObfuscated ? "()V" : "()V";
        for (MethodNode method : classNode.methods) {
            if (method.name.equals(method_name) && method.desc.equals(method_dec)) {
                AbstractInsnNode targetNode = null;
                for (AbstractInsnNode instruction : method.instructions.toArray())
                {
                    if (instruction.getOpcode() ==INVOKEVIRTUAL )
                    {
                        if ((((MethodInsnNode) instruction).owner.equals("net/minecraft/item/ItemStack")||((MethodInsnNode) instruction).owner.equals("adq"))&&instruction.getPrevious().getOpcode()== ALOAD)
                        {
                            targetNode = instruction;
                            break;
                        }
                    }
                }
                if (targetNode != null)
                {
                    /*
                    Replacing:
                        if (itemstack != null && itemstack.getItem() == Items.ELYTRA && ItemElytra.isBroken(itemstack))
                    With:
                        if(ElytraEvents.check(this))

                    BYTECODE

                    Replacing:
                        mv.visitVarInsn(ALOAD, 2);
                        Label l5 = new Label();
                        mv.visitJumpInsn(IFNULL, l5);
                        mv.visitVarInsn(ALOAD, 2);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "getItem", "()Lnet/minecraft/item/Item;", false);
                        mv.visitFieldInsn(GETSTATIC, "net/minecraft/init/Items", "ELYTRA", "Lnet/minecraft/item/Item;");
                        mv.visitJumpInsn(IF_ACMPNE, l5);
                        mv.visitVarInsn(ALOAD, 2);
                        mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/item/ItemElytra", "isBroken", "(Lnet/minecraft/item/ItemStack;)Z", false);
                        mv.visitJumpInsn(IFEQ, l5);

                    With:
                        ALOAD 0
                        INVOKESTATIC ElytraEvents.check
                        mv.visitJumpInsn(IFEQ, l5);

                     */
                    for (int i = 0; i < 3; i++){
                        targetNode = targetNode.getPrevious();
                    }
                    for (int i = 0; i < 8; i++)
                    {
                        targetNode = targetNode.getNext();
                        method.instructions.remove(targetNode.getPrevious());
                    }

                    InsnList toInsert = new InsnList();
                    toInsert.add(new VarInsnNode(ALOAD, 0));
                    toInsert.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ElytraEvents.class), "check", isObfuscated ? "(Lsa;)Z" : "(Lnet/minecraft/entity/EntityLivingBase;)Z", false));
                    method.instructions.insertBefore(targetNode, toInsert);
                }
                else
                {
                    System.out.println("Something went wrong transforming EntityLivingBase! #1");
                }
                targetNode=null;
                for (AbstractInsnNode instruction : method.instructions.toArray())
                {
                    if (instruction.getOpcode() ==INVOKEVIRTUAL )
                    {
                        if ((((MethodInsnNode) instruction).owner.equals("net/minecraft/item/ItemStack")
                                ||((MethodInsnNode) instruction).owner.equals("adq"))
                                &&(((MethodInsnNode) instruction).name.equals("damageItem")
                                ||((MethodInsnNode) instruction).name.equals("a")))
                        {
                            targetNode = instruction;
                            break;
                        }
                    }
                }
                if (targetNode != null)
                {
                    /*
                    Replacing:
                        if (itemstack != null && itemstack.getItem() == Items.ELYTRA && ItemElytra.isBroken(itemstack))
                    With:
                        if(ElytraEvents.check(this))

                    BYTECODE

                    Replacing:
                        mv.visitVarInsn(ALOAD, 2);
                        mv.visitInsn(ICONST_1);
                        mv.visitVarInsn(ALOAD, 0);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "damageItem", "(ILnet/minecraft/entity/EntityLivingBase;)V", false);


                    With:
                        ALOAD 0
                        INVOKESTATIC ElytraEvents.damageOnFlying


                     */
                    for (int i = 0; i < 3; i++){
                        targetNode = targetNode.getPrevious();
                    }
                    for (int i = 0; i < 4; i++)
                    {
                        targetNode = targetNode.getNext();
                        method.instructions.remove(targetNode.getPrevious());
                    }


                    InsnList toInsert = new InsnList();
                    toInsert.add(new VarInsnNode(ALOAD, 0));
                    toInsert.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ElytraEvents.class), "damageOnFlying", isObfuscated ? "(Lsa;)V" : "(Lnet/minecraft/entity/EntityLivingBase;)V", false));
                    method.instructions.insertBefore(targetNode, toInsert);
                }
                else
                {
                    System.out.println("Something went wrong transforming EntityLivingBase! #2");
                }

            }

        }

    }

    private static void transformElytraCheckEntityPlayerSP(ClassNode classNode,boolean isObfuscated){
        String method_name = isObfuscated ? "n" : "onLivingUpdate";
        String method_dec = isObfuscated ? "()V" : "()V";
        for (MethodNode method : classNode.methods) {
            if (method.name.equals(method_name) && method.desc.equals(method_dec)) {
                AbstractInsnNode targetNode = null;
                for (AbstractInsnNode instruction : method.instructions.toArray())
                {
                    if (instruction.getOpcode() ==GETSTATIC )
                    {
                        if (((FieldInsnNode) instruction).name.equals("ELYTRA")||((FieldInsnNode) instruction).name.equals("cR"))
                        {
                            targetNode = instruction;
                            break;
                        }
                    }
                }
                if (targetNode != null)
                {
                    /*
                    Replacing:
                        if (itemstack != null && itemstack.getItem() == Items.ELYTRA && ItemElytra.isBroken(itemstack))
                    With:
                        if(ElytraEvents.check(this))

                    BYTECODE

                    Replacing:
                        mv.visitVarInsn(ALOAD, 7);
                        mv.visitJumpInsn(IFNULL, l65);
                        mv.visitVarInsn(ALOAD, 7);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "getItem", "()Lnet/minecraft/item/Item;", false);
                        mv.visitFieldInsn(GETSTATIC, "net/minecraft/init/Items", "ELYTRA", "Lnet/minecraft/item/Item;");
                        mv.visitJumpInsn(IF_ACMPNE, l65);
                        mv.visitVarInsn(ALOAD, 7);
                        mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/item/ItemElytra", "isBroken", "(Lnet/minecraft/item/ItemStack;)Z", false);
                    With:
                        ALOAD 0
                        INVOKESTATIC ElytraEvents.check

                     */
                    for (int i = 0; i < 4; i++){
                        targetNode = targetNode.getPrevious();
                    }
                    for (int i = 0; i < 8; i++)
                    {
                        targetNode = targetNode.getNext();
                        method.instructions.remove(targetNode.getPrevious());
                    }

                    InsnList toInsert = new InsnList();
                    toInsert.add(new VarInsnNode(ALOAD, 0));
                    toInsert.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ElytraEvents.class), "check", isObfuscated ? "(Lsa;)Z" : "(Lnet/minecraft/entity/EntityLivingBase;)Z", false));
                    method.instructions.insertBefore(targetNode, toInsert);
                }
                else
                {
                    System.out.println("Something went wrong transforming EntityPlayerSP! #1");
                }


            }

        }

    }

    private static void transformElytraCheckEntityNetHandlerPlayServer(ClassNode classNode,boolean isObfuscated){
        String method_name = isObfuscated ? "a" : "processEntityAction";
        String method_dec = isObfuscated ? "(Liz;)V" : "(Lnet/minecraft/network/play/client/CPacketEntityAction;)V";
        for (MethodNode method : classNode.methods) {
            if (method.name.equals(method_name) && method.desc.equals(method_dec)) {
                AbstractInsnNode targetNode = null;
                for (AbstractInsnNode instruction : method.instructions.toArray())
                {
                    if (instruction.getOpcode() ==GETSTATIC )
                    {
                        if (((FieldInsnNode) instruction).name.equals("ELYTRA")||((FieldInsnNode) instruction).name.equals("cR"))
                        {
                            targetNode = instruction;
                            break;
                        }
                    }
                }
                if (targetNode != null)
                {
                    /*
                    Replacing:
                        if (itemstack != null && itemstack.getItem() == Items.ELYTRA && ItemElytra.isBroken(itemstack))
                    With:
                        if(ElytraEvents.check(this))

                    BYTECODE

                    Replacing:
                        mv.visitVarInsn(ALOAD, 2);
                        mv.visitJumpInsn(IFNULL, l32);
                        mv.visitVarInsn(ALOAD, 2);
                        mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/item/ItemStack", "getItem", "()Lnet/minecraft/item/Item;", false);
                        mv.visitFieldInsn(GETSTATIC, "net/minecraft/init/Items", "ELYTRA", "Lnet/minecraft/item/Item;");
                        mv.visitJumpInsn(IF_ACMPNE, l32);
                        mv.visitVarInsn(ALOAD, 2);
                        mv.visitMethodInsn(INVOKESTATIC, "net/minecraft/item/ItemElytra", "isBroken", "(Lnet/minecraft/item/ItemStack;)Z", false);
                    With:
                        ALOAD 0
                        INVOKESTATIC ElytraEvents.check

                     */
                    for (int i = 0; i < 4; i++){
                        targetNode = targetNode.getPrevious();
                    }
                    for (int i = 0; i < 8; i++)
                    {
                        targetNode = targetNode.getNext();
                        method.instructions.remove(targetNode.getPrevious());
                    }

                    InsnList toInsert = new InsnList();
                    toInsert.add(new VarInsnNode(ALOAD, 0));
                    toInsert.add(new FieldInsnNode(GETFIELD,
                            isObfuscated?"mc":"net/minecraft/network/NetHandlerPlayServer",
                            isObfuscated?"b":"playerEntity",
                            isObfuscated?"Lls;":"Lnet/minecraft/entity/player/EntityPlayerMP;"));
                    toInsert.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ElytraEvents.class), "check", isObfuscated ? "(Lsa;)Z" : "(Lnet/minecraft/entity/EntityLivingBase;)Z", false));
                    method.instructions.insertBefore(targetNode, toInsert);
                }
                else
                {
                    System.out.println("Something went wrong transforming EntityNetHandlerPlayServer! #1");
                }


            }

        }

    }

}
