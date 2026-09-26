#include <stdio.h>

#define CLAY_IMPLEMENTATION
#include "lib/clay.h"
#include "lib/clay_renderer_raylib.c"
#include "lib/raylib.h"

int main(void) {
  Clay_Raylib_Initialize(800, 400, "ui in clay!", FLAG_WINDOW_RESIZABLE);
  return 0;
}
