require abaco-test-image.bb

DESCRIPTION = "Abaco Dev Image"

IMAGE_FEATURES += " dev-pkgs"

IMAGE_INSTALL += " \
	kernel-devsrc \
	man-pages \
	git \
	packagegroup-core-buildessential \
	packagegroup-core-device-devel \
"
