import * as React from "react";

import { cn } from "@/lib/utils";
import { cva, type VariantProps } from "class-variance-authority";

const iconVariants = cva("shrink-0", {
  variants: {
    size: {
      xs: "h-4 w-4",
      sm: "h-6 w-6",
      md: "h-8 w-8",
      lg: "h-16 w-16",
    },
  },
  defaultVariants: {
    size: "xs",
  },
});

export interface IconProps
  extends React.ComponentPropsWithoutRef<"svg">,
    VariantProps<typeof iconVariants> {
  i: React.ElementType;
}

const Icon = React.forwardRef<SVGSVGElement, IconProps>(
  ({ i: I, className, size, ...props }, ref) => {
    return (
      <I
        ref={ref}
        className={cn(iconVariants({ size, className }))}
        {...props}
      />
    );
  },
);
Icon.displayName = "Icon";

export { Icon, iconVariants };
