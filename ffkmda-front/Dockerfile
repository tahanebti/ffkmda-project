#### Stage 1: Build the angular application
FROM node as build


WORKDIR /app

RUN npm cache clean --force

#COPY ./package.json .

#RUN npm install
#COPY . .
#RUN npm run build --prod

#RUN npm start

#### Stage 2, use the compiled app, ready for production with Nginx
#FROM nginx

# Copy the angular build from Stage 1
#COPY --from=build /app/dist /etc/nginx/html

# Copy our custom nginx config
#COPY /nginx-custom.conf /etc/nginx/conf.d/default.conf


#ENTRYPOINT ["nginx","-g","daemon off;"]