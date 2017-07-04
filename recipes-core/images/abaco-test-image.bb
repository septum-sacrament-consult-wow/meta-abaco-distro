require abaco-core-image.bb

DESCRIPTION = "Abaco Test Image"

IMAGE_INSTALL += " \
	packagegroup-core-full-cmdline \
	vim \
	openssh-sftp-server \
	bash \
	i2c-tools \
	ethtool \
"
