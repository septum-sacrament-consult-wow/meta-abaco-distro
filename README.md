## Abaco Development Manifests Repo

---

Provides Google Repo manifest files to help with easy cloning of git
repositories along with a set of helper scripts to aid building for Abaco
platforms.

Please setup your host build environmnet as per the instructions on the [Yocto Project Quickstart](https://www.yoctoproject.org/docs/1.8/yocto-project-qs/yocto-project-qs.html) page.

### Usage

Ensure you have a working copy of the *Repo* tool

    curl https://storage.googleapis.com/git-repo-downloads/repo > repo && chmod +x repo

Initialise your build

```
mkdir abaco-build
cd abaco-build
python2 ../repo init -u https://github.com/Abaco-Systems/abaco-bsp-manifests.git -m manifests-[release|dev]/<machinename>_branch.xml
python2 ../repo sync
rm ../repo
```
i.e For SBC314 use:
```
python2 ../repo init -u https://github.com/Abaco-Systems/abaco-bsp-manifests.git -m manifests-dev/sbc314_pyro.xml
```

You should now have a directory heirarchy with all the required Abaco platform
repos checked out.

### Building

To start a build run

    ./init-new-build

### Releasing

To release, commit all repository changes and run the following command

    repo manifest -r -o machine_branch.xml

This will generate a new manifest file with the current set of git
repository commit hashes locked. This file can then be copied to the
manfiest folder with the filename as machine_branch.xml, commited
and tagged with the release version.

## Booting
After a successfull build you should find the following files in /<your-install-root>/abaco-build/build-sbc314-t1042/tmp-glibc/deploy/images/sbc314-t1042. This is for T1042 core-image-minimal build:
```
core-image-minimal-sbc314-t1042-20171124110726.rootfs.ext2.gz
core-image-minimal-sbc314-t1042-20171124110726.rootfs.ext2.gz.u-boot
core-image-minimal-sbc314-t1042-20171124110726.rootfs.manifest
core-image-minimal-sbc314-t1042-20171124110726.rootfs.tar.gz
core-image-minimal-sbc314-t1042-20171124110726.testdata.json
core-image-minimal-sbc314-t1042.ext2.gz
core-image-minimal-sbc314-t1042.ext2.gz.u-boot
core-image-minimal-sbc314-t1042.manifest
core-image-minimal-sbc314-t1042.tar.gz
core-image-minimal-sbc314-t1042.testdata.json
modules--4.1+git0+f881c16a75-r0-sbc314-t1042-20171124110726.tgz
modules-sbc314-t1042.tgz
uImage
uImage--4.1+git0+f881c16a75-r0-sbc314-t1042-20171124110726.bin
uImage--4.1+git0+f881c16a75-r0-sbc314-t1042-20171124110726.dtb
uImage-sbc314-t1042.bin
uImage-sbc314-t1042.dtb
```
Boot your target into u-boot (this should be factory installed).

Flash the images into RAM, example below is for the SBC314

### Network booting
This is the most convinient way to boot the board when rebuilding the filesystem and kernel image.
```
bootargs "console=tty0 console=ttyS0,115200n8"
setenv bootcmd "run boottftp"
setenv boottftp "tftpb 1000000 uImage && tftpb e00000 uImage-sbc314-t1042.dtb && tftpb 4000000 core-image-minimal-sbc314-t1042-20171124110726.rootfs.ext2.gz.u-boot &&  bootm 1000000 4000000 e00000"
setenv bootargs "console=ttyS0,115200n8"
```
Verify the settings use the print command:

```
bootargs=console=tty0 console=ttyS0,115200n8
bootcmd=run boottftp
boottftp=tftpb 1000000 uImage && tftpb e00000 uImage-sbc314-t1042.dtb && tftpb 4000000 core-image-minimal-sbc314-t1042-20171124110726.rootfs.ext2.gz.u-boot &&  bootm 1000000 4000000 e00000
ipaddr=192.168.1.240
netmask=255.255.255.0
serverip=192.168.1.97
```

## Modifying
You can used the GUI toaster to modify a recipe. See Yocto usage for [toaster](https://www.yoctoproject.org/tools-resources/projects/toaster) usage.
```
sudo apt install python3-pip
pip3 install -r /<your-install-root>/abaco-build/openembedded/bitbake.git/toaster-requirements.txt
. /<your-install-root>/abaco-build/openembedded/bitbake.git/bin/toaster
...
Please enter the path of your openembedded-core layer: /<your-install-root>/abaco-build
...
```
### XFCE Desktop Environment
You will need to add the missing layers if you wish to use **XFCE**. Example below shown for SBC314 with QorIQ T1042 and instructions need to be modified for the T2081.
```
cd /<your-install-root>/abaco-build/openembedded
git clone git://git.openembedded.org/meta-openembedded
```
Edit *\/\<your-install-root\>/abaco-build/conf/bblayers.conf* and add the following to **BBLAYERS  ?= "**
```
  ...
  /<your-install-root>/abaco-build/openembedded/meta-openembedded/meta-xfce \
  /<your-install-root>/abaco-build/openembedded/meta-openembedded/meta-gnome \
  /<your-install-root>/abaco-build/openembedded/meta-openembedded/meta-multimedia \
  /<your-install-root>/abaco-build/openembedded/meta-openembedded/meta-perl \
  /<your-install-root>/abaco-build/openembedded/meta-openembedded/meta-python \
  /<your-install-root>/abaco-build/openembedded/meta-openembedded/meta-networking \
```
Add additional packages to the end of *\/\<your-install-root\>/abaco-build/conf/local.conf*
```
IMAGE_INSTALL_append = "eudev e2fsprogs openssh nano mesa-demos pciutils usbutils gcc xterm xclock xf86-input-evdev epiphany"
```
Build your image
```
cd /<your-install-root>/abaco-build/build-sbc314-t1042
bitbake core-image-minimal-xfce
```
Make a cup of tea and wait...
