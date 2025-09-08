FROM mcr.microsoft.com/dotnet/aspnet:9.0 AS base
USER $APP_UID
WORKDIR /app
EXPOSE 5050

FROM mcr.microsoft.com/dotnet/sdk:9.0 AS build
ARG BUILD_CONFIGURATION=Release
WORKDIR /src
COPY ./nuget.config ./
COPY ["Jimx.ListItemSelector.Api/Jimx.ListItemSelector.Api.csproj", "Jimx.ListItemSelector.Api/"]
COPY ["Jimx.ListItemSelector.Application/Jimx.ListItemSelector.Application.csproj", "Jimx.ListItemSelector.Application/"]
COPY ["Jimx.ListItemSelector.Domain/Jimx.ListItemSelector.Domain.csproj", "Jimx.ListItemSelector.Domain/"]
COPY ["Jimx.ListItemSelector.Infrastructure/Jimx.ListItemSelector.Infrastructure.csproj", "Jimx.ListItemSelector.Infrastructure/"]
RUN dotnet restore "Jimx.ListItemSelector.Api/Jimx.ListItemSelector.Api.csproj"
COPY . .
WORKDIR "/src/Jimx.ListItemSelector.Api"
RUN dotnet build "./Jimx.ListItemSelector.Api.csproj" -c $BUILD_CONFIGURATION -o /app/build

FROM build AS publish
ARG BUILD_CONFIGURATION=Release
RUN dotnet publish "./Jimx.ListItemSelector.Api.csproj" -c $BUILD_CONFIGURATION -o /app/publish /p:UseAppHost=false

FROM base AS final
WORKDIR /app
ENV ASPNETCORE_URLS=http://+:5050
COPY --from=publish /app/publish .
ENTRYPOINT ["dotnet", "Jimx.ListItemSelector.Api.dll"]
