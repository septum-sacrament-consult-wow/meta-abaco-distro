#! /usr/bin/env python3

import subprocess, sys, re, argparse, os, shutil, json

def main():

	parser = argparse.ArgumentParser(description='Manage the build tree')

	parser.add_argument('--layers', help='JSON List of layers to add to build',
		default='default')

	args = parser.parse_args()

	if args.layers is None:
		print_info("No layers to add, exiting")
		exit(0)

	if not shutil.which("bitbake-layers"):
		print_error("missing 'bitbake-layers' command, please ensure "
			"bitbake environment is sourced")
		exit(1)

	try:
		layers = json.loads(args.layers)
	except:
		print_error("Failed to load json layers list")
		exit(1)

	added_layers = []
	layers = list(set(layers))
	layer_count = len(layers)

	print_info("Adding layers, this may take a while...")

	while len(added_layers) != layer_count:
		
		added_layers_count = len(added_layers)

		for layer in layers[:]:
			if add_layer(layer):
				added_layers.append(layer)
				layers.remove(layer)

		if len(added_layers) == added_layers_count:
			print_error("Some layers could not be added, check dependant layers "
				"exist, and for circular dependancies")
			print_info("Layers added: {}".format(added_layers))
			print_error("Layers failed: {}".format(layers))
			exit(1)

	print_info("All layers sucessfully added")	
		

def add_layer(layer):

	cmd = ["bitbake-layers", "add-layer",  layer]

	try:
		output = subprocess.check_output(cmd, stderr=subprocess.STDOUT)
		print_info("Added layer {} sucessfully".format(layer))

	except subprocess.CalledProcessError as e:
		return False

	return True

def print_info(text):

	print('\x1b[0;32m' + 'INFO: ' + '\x1b[0m' + text)

def print_error(text):

	print('\x1b[0;31m' + 'ERROR: ' + '\x1b[0m' + text)

if __name__ == '__main__':
	main()
