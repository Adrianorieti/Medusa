#!/usr/bin/env python
import struct
import lief
from lief.MachO import LOAD_COMMAND_TYPES, HEADER_FLAGS


def check(filename):
  macho = lief.parse(filename)

  # PIE
  pie_enabled = HEADER_FLAGS.PIE in macho.header.flags_list

  # restrict segment for anti-debugging
  # ptrace looks hard to detect by pure static analytics
  restricted_segment = False
  for segment in macho.segments:
    if segment.name.lower() == '__restrict':
      restricted_segment = True
      break

  imported = macho.imported_functions

  # stack canary
  canary_enabled = '___stack_chk_fail' in imported and '___stack_chk_guard' in imported

  # encrypted
  cryptid = 0
  for cmd in macho.commands:
    if cmd.command == LOAD_COMMAND_TYPES.ENCRYPTION_INFO:
      buf = bytearray(cmd.data)
      cmd, cmdsize, cryptoff, cryptsize, cryptid = struct.unpack('<IIIII', buf)
      break

    elif cmd.command == LOAD_COMMAND_TYPES.ENCRYPTION_INFO_64:
      buf = bytearray(cmd.data)
      cmd, cmdsize, cryptoff, cryptsize, cryptid, pad = struct.unpack('<IIIIII', buf)
      break

  encrypted = bool(cryptid)

  # NX  
  nx = 0

  if(str(macho.has_nx)):
    nx = True
  
  result = {
    'RESTRICT': restricted_segment,
    'CANARY': canary_enabled,
    'PIE': pie_enabled,
    'ENCRYPTED': encrypted,
    'NX':    nx,
  }

  return result


