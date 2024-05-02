import { forwardRef } from "react";

import { Icon, iconVariants } from "./icon";

import { cn } from "@/lib/utils";
import { VariantProps } from "class-variance-authority";
import { Loader2 } from "lucide-react";

export interface LoadingProps
  extends React.ComponentPropsWithoutRef<"svg">,
    VariantProps<typeof iconVariants> {}

const Loading = forwardRef<SVGSVGElement, LoadingProps>(({ ...props }, ref) => {
  return (
    <Icon
      {...props}
      i={Loader2}
      className={cn("animate-spin", props.className)}
      ref={ref}
    />
  );
});
Loading.displayName = "Loading";

export { Loading, iconVariants };
