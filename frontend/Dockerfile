# Install dependencies only when needed
FROM node:18-alpine AS deps
# Check https://github.com/nodejs/docker-node/tree/b4117f9333da4138b03a546ec926ef50a31506c3#nodealpine to understand why libc6-compat might be needed.
RUN apk add --no-cache libc6-compat
WORKDIR /front/app

# Install dependencies based on the preferred package manager
COPY package.json package-lock.json* pnpm-lock.yaml* next.config.mjs ./
RUN corepack enable
RUN npm install


# Rebuild the source code only when needed
FROM node:18-alpine AS builder
WORKDIR /front/app
COPY --from=deps /front/app/node_modules ./node_modules
COPY . .

# Next.js collects completely anonymous telemetry data about general usage.
# Learn more here: https://nextjs.org/telemetry
# Uncomment the following line in case you want to disable telemetry during the build.
ENV NEXT_TELEMETRY_DISABLED 1
RUN npm run build

# # If using npm comment out above and use below instead
# # RUN npm run build

# # Production image, copy all the files and run next
# FROM node:18-alpine AS runner
WORKDIR /front/app

ENV NODE_ENV production
# Uncomment the following line in case you want to disable telemetry during runtime.
ENV NEXT_TELEMETRY_DISABLED 1

RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs

# # You only need to copy next.config.js if you are NOT using the default configuration
# # COPY --from=builder /front/app/next.config.js ./
# COPY --from=builder /front/app/public ./public
# COPY --from=builder /front/app/package.json ./package.json
# COPY --from=builder /front/app/.env ./.env


# Add chown and permission to files to avoid permission denied
RUN chown -R nextjs:nodejs /front/app/.next
RUN chown -R nextjs:nodejs /front/app/node_modules
RUN chown -R nextjs:nodejs /front/app/public
RUN chown -R nextjs:nodejs /front/app/package.json
RUN chown -R nextjs:nodejs /front/app/.env

USER nextjs

EXPOSE 3000

ENV PORT 3000

ENTRYPOINT ["npm", "start"]