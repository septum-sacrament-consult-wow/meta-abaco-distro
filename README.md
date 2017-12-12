![Abaco stripe](https://github.com/ross-abaco/rtp-motion-estimation/blob/master/abaco/Abaco_background-1000x275.png?raw=true)
## Abaco Development Manifests Repo


Before you begin make sure your machine is setup correctly for Yocto. For CentOS / Ubuntu you can run:

    sudo apt-get install gawk wget git-core diffstat unzip texinfo gcc-multilib  build-essential chrpath socat libsdl1.2-dev xterm

Setup your host build environmnet as per the instructions on the [Yocto Project Quickstart](https://www.yoctoproject.org/docs/1.8/yocto-project-qs/yocto-project-qs.html) page.

Provides Google Repo manifest files to help with easy cloning of git
repositories along with a set of helper scripts to aid building for Abaco
platforms.

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

### U-Boot (over network)
This is the most convinient way to setup u-boot when rebuilding the filesystem and kernel image on a host machine.
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
### U-Boot (from flash / ssd)
TBC ...
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
IMAGE_INSTALL_append = "exfat-utils e2fsprogs gptfdisk openssh nano mesa-demos pciutils usbutils gcc xterm xclock xf86-input-evdev"
```
Build your image
```
cd /<your-install-root>/abaco-build/build-sbc314-t1042
bitbake core-image-minimal-xfce
```
Make a cup of tea and wait...

#### Rebuilding the kernel
To run menuconfig and rebuild the kernel to add in the required modules.
```
sudo apt-get install screen
cd /<your-install-root>/abaco-build/build-sbc314-t1042
bitbake virtual/kernel -c menuconfig
```
The resulting .config file needs to be located in */<your-install-root>/abaco-build/.config*
```
bitbake virtual/kernel -c cleansstate
bitbake virtual/kernel -c compile; bitbake virtual/kernel
```
You might want to consider adding the follwing modules for XFDE4 desktop environment to work with mouse and keyboard devices:
```
CONFIG_INPUT_KEYBOARD=y
CONFIG_INPUT_MOUSE=y
CONFIG_USB_KBD=y
CONFIG_USB_MOUSE=y
CONFIG_INPUT_EVDEV=y
```
#### XFCE4 execution
When the OS is running start the desktop environment:
```
startxfce4
```
![XFCE4 Desktop](/images/PPC-Yocto-XFCE4.png)
**XFCE4 running on the SBC314 (T1042) with XMCGA8 for graphics (Single 3U VPX Solution).**

![Abaco footer](https://github.com/ross-abaco/rtp-motion-estimation/blob/master/abaco/Abaco%20Footer1000x100.png)

