require abaco-base-image.bb

DESCRIPTION = "Abaco Core Image"

IMAGE_FEATURES += " ssh-server-openssh"

IMAGE_INSTALL += " \
	gptfdisk \
	pciutils \
	usbutils \
	bash \
	i2c-tools \
"

