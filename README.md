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
After a successfull build you should find the following files in /<your-install-root>/abaco-build/build-sbc314-t1042/tmp-glibc/deploy/images/sbc314-t1042

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
Boot your target into u-boot (this should be factory installed.

Flash the images into RAM, example below is for the SBC314
```
TODO: Add example
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


