SUMMARY = "Abaco Base Image"

require recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL += " \
	packagegroup-base \
	ethtool \
	iperf3 \
	mtd-utils \
	tftp-hpa \
	util-linux \
	wget \
"
